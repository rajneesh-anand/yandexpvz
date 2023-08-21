package com.neo.yandexpvz.screens.signin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.model.UserRequest
import com.neo.yandexpvz.model.UserUIState
import com.neo.yandexpvz.repository.CoinRepository
import com.neo.yandexpvz.repository.DataStoreRepository
import com.neo.yandexpvz.repository.UserRepository
import com.neo.yandexpvz.utils.NetworkResult
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
    private val coinRepository: CoinRepository,
    private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {


    var isLoading: Boolean by mutableStateOf(false)
    var userLoginStatus: Boolean by mutableStateOf(false)
    private var fcmToken: String =""

    var uiState = mutableStateOf(UserUIState())
        private set


    private val password
        get() = uiState.value.password

    private val mobile
        get() = uiState.value.mobile

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveOnBoardingState(completed = true)
            dataStoreRepository.readFCMTokenState().collect {
                token -> fcmToken = token
            }
        }
    }



    fun onMobileChange(newValue: String) {
        uiState.value = uiState.value.copy(mobile = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick() {
        isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response =
                    userRepository.loginUser(UserRequest("", password, mobile, "",fcmToken ))) {
                    is NetworkResult.Success -> {
                        tokenManager.saveToken(response.data!!.token, response.data!!.user)
                        coinRepository.insertCoins(response.data!!.user.mobile)
                        userLoginStatus = true
                        isLoading = false
                    }
                    is NetworkResult.Error -> {
                        isLoading = false
                        uiState.value = uiState.value.copy(errorText = response.message.toString())
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