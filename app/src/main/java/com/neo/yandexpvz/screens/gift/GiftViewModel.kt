package com.neo.yandexpvz.screens.gift

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.model.GiftCard
import com.neo.yandexpvz.repository.GiftRepository
import com.neo.yandexpvz.utils.NetworkResult
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GiftViewModel @Inject constructor(
    private val giftRepository: GiftRepository,
    private val tokenManager: TokenManager,
): ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var errorText: String by mutableStateOf("")
    private var _giftsList: MutableLiveData<List<GiftCard>> = MutableLiveData(listOf())
    val giftList: LiveData<List<GiftCard>> = _giftsList


    fun initialize(){
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
             try {
                when (val response = giftRepository.fetchUserGiftCard(tokenManager.getUserMobile().toString())) {
                    is NetworkResult.Success -> {
                        _giftsList.postValue(response.data!!.results)
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
}