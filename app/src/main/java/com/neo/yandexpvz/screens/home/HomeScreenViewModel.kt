package com.neo.yandexpvz.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.PRODUCT_ID
import com.neo.yandexpvz.PRODUCT_SCREEN
import com.neo.yandexpvz.model.Product
import com.neo.yandexpvz.model.UserHomeUIState
import com.neo.yandexpvz.repository.CoinRepository
import com.neo.yandexpvz.repository.ProductRepository
import com.neo.yandexpvz.utils.NetworkResult
import com.neo.yandexpvz.utils.TokenManager
import com.neo.yandexpvz.worker.PeriodicDatabaseSyncWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val productRepository: ProductRepository,
    private val tokenManager: TokenManager,
    private val workManager: PeriodicDatabaseSyncWorkManager,
    ): ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)

    private val _homeState = MutableStateFlow(UserHomeUIState())
    val homeState: StateFlow<UserHomeUIState> = _homeState.asStateFlow()

    private var _productsList: MutableLiveData<List<Product>> = MutableLiveData(listOf())
    val productList: LiveData<List<Product>> = _productsList

    val balancedCoins = coinRepository.balancedCoins


    val name by mutableStateOf(tokenManager.getUserName().toString())
    val email by mutableStateOf(tokenManager.getUserEmail().toString())
    val mobile by mutableStateOf(tokenManager.getUserMobile().toString())
    val userImage by mutableStateOf(tokenManager.getUserImage().toString())




//    val productsList = coinRepository.productsList



   private fun fetchCoinBalance(){
         viewModelScope.launch(Dispatchers.IO) {
             coinRepository.fetchUserCoinBalance(tokenManager.getUserMobile().toString())
        }
    }
//
//    private fun fetchProductsList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            coinRepository.fetchProductsList()
//        }
//    }

    fun onProductClick(openScreen: (String) -> Unit, product: Product) {
           openScreen("$PRODUCT_SCREEN?$PRODUCT_ID=${product.id}")
    }
//
//    fun fetchUserDetails(){
//        fetchCoinBalance()
//        fetchProductsList()
//        workManager.startPeriodicNotifications()
//    }

    fun initialize() {
        isLoading = true
        _homeState.update { it.copy( name = tokenManager.getUserName().toString()) }
        _homeState.update { it.copy(  email = tokenManager.getUserEmail().toString()) }
        _homeState.update { it.copy(   mobile = tokenManager.getUserMobile().toString()) }
        _homeState.update { it.copy(   image = tokenManager.getUserImage().toString()) }

        fetchCoinBalance()
        workManager.startPeriodicNotifications()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response = productRepository.fetchProductList()) {
                    is NetworkResult.Success -> {
//                        _homeState.value = UserHomeUIState(errorText = "")
                        _homeState.update { it.copy( errorText = "") }
                        _productsList.postValue(response.data!!.results)
                        isLoading = false
                    }
                    is NetworkResult.Error -> {
                        isLoading = false
//                        errorText = response.message.toString()
//                        _homeState.value = UserHomeUIState(errorText = response.message.toString())
                        _homeState.update { it.copy( errorText = response.message.toString()) }

                    }
                    else -> {
                        isLoading = false
//                        _homeState.value = UserHomeUIState(errorText = response.message.toString())
                        _homeState.update { it.copy( errorText = response.message.toString()) }
                    }
                }

            } catch (exception: Exception) {
                isLoading = false
//                errorText = "Oops ! Something went wrong "
//                _homeState.value = UserHomeUIState(errorText ="Oops ! Something went wrong ")
                _homeState.update { it.copy( errorText ="Oops ! Something went wrong ") }
                Log.d("Network", "server down: ${exception.message.toString()}")
            }

        }
    }


}