package com.neo.yandexpvz.screens.product

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
import com.neo.yandexpvz.model.RedeemRequest
import com.neo.yandexpvz.repository.CoinRepository
import com.neo.yandexpvz.repository.ProductRepository
import com.neo.yandexpvz.utils.NetworkResult
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val coinRepository: CoinRepository,
    private val tokenManager: TokenManager,
): ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var errorText: String by mutableStateOf("")
    var redeemID: String by mutableStateOf("")
    var redeemMessage: String by mutableStateOf("")

    private var _productInfo: MutableLiveData<Product> = MutableLiveData()
    val productInfo: LiveData<Product> = _productInfo


    fun initialize(productId: String){
         viewModelScope.launch(Dispatchers.IO) {
             isLoading = true
             try {
            when (val response = productRepository.fetchProduct(productId)) {
                 is NetworkResult.Success -> {
                _productInfo.postValue(response.data!!.product)
                isLoading = false
                }
                is NetworkResult.Error -> {
                    isLoading = false
                    errorText = response.message.toString()
                }
                else -> {
                    isLoading = false
                }
            }

        } catch (exception: Exception) {
            isLoading = false
            Log.d("Network", "loginUser: ${exception.message.toString()}")
        }

         }
   }


    fun onProductClick(openScreen: (String) -> Unit, product: Product) {
        openScreen("$PRODUCT_SCREEN?$PRODUCT_ID=${product.id}")
    }


    fun onRedeemCoin(){
        val name = tokenManager.getUserName().toString()
        val mobile = tokenManager.getUserMobile().toString()
        val email = tokenManager.getUserEmail().toString()

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            try {
                when (val response =
                    productRepository.redeemCoins(RedeemRequest(
                        email,mobile,name,_productInfo.value?.coinValue.toString(),
                        _productInfo.value!!.name,_productInfo.value?.coinValue.toString()
                       ))) {
                    is NetworkResult.Success -> {
                        coinRepository.insertCoins(mobile)
                        redeemID = response.data!!.redeemId
                        redeemMessage= "success"
                        isLoading = false

                    }

                    is NetworkResult.Error -> {
                        isLoading = false
                        errorText = response.message.toString()
                    }

                    else -> {
                        isLoading = false
                    }
                }

            } catch (exception: Exception) {
                isLoading = false
                errorText = "Something went wrong !"
                Log.d("Network", "loginUser: ${exception.message.toString()}")
            }

        }



    }
}