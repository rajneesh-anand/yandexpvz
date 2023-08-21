package com.neo.yandexpvz.screens.signup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.model.UserRequest
import com.neo.yandexpvz.model.UserUIState
import com.neo.yandexpvz.repository.DataStoreRepository
import com.neo.yandexpvz.repository.UserRepository
import com.neo.yandexpvz.utils.NetworkResult
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var userRegisterStatus:Boolean  by mutableStateOf(false)
    private var fcmToken: String =""

    var uiState = mutableStateOf(UserUIState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    private val mobile
        get() = uiState.value.mobile

    private val name
        get() = uiState.value.name

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.readFCMTokenState().collect {
                    token -> fcmToken = token
            }
        }
    }


    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }


    fun onMobileChange(newValue: String) {
        uiState.value = uiState.value.copy(mobile = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


    fun onSignUpClick() {
        isLoading=true

        viewModelScope.launch(Dispatchers.Default) {

            try {
                when(val response =   userRepository.registerUser(UserRequest(email,password,mobile,name,fcmToken))) {
                    is NetworkResult.Success -> {
                        isLoading = false
                        tokenManager.saveToken(response.data!!.token,response.data!!.user)
                        userRegisterStatus = true
                    }
                    is NetworkResult.Error -> {
                        isLoading = false
                        uiState.value = uiState.value.copy(errorText =  response.message.toString())

                    }
                    else -> {isLoading = false}
                }

            }catch (exception: Exception){
                isLoading = false
                Log.d("Network", "loginUser: ${exception.message.toString()}")
            }

        }

    }

}