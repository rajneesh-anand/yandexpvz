package com.neo.yandexpvz.screens.password

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.model.UserRequest
import com.neo.yandexpvz.model.UserUIState
import com.neo.yandexpvz.repository.UserRepository
import com.neo.yandexpvz.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val userRepository: UserRepository,
    ) : ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var passwordUpdateStatus:Boolean  by mutableStateOf(false)

    var uiState = mutableStateOf(UserUIState())
        private set


    private val password
        get() = uiState.value.password

    private val mobile
        get() = uiState.value.mobile




    fun onMobileChange(newValue: String) {
        uiState.value = uiState.value.copy(mobile = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


    fun onUpdatePasswordClick() {
        isLoading=true
//        if (mobile.isBlank() || !mobile.isValidMobile()) {
//            SnackbarManager.showMessage(R.string.mobile_error)
//            return
//        }

//
//        if (password.isBlank() || !password.isValidPassword()) {
//            SnackbarManager.showMessage(R.string.password_error)
//            return
//        }



        viewModelScope.launch(Dispatchers.Default) {

            try {
                when(val response =   userRepository.updatePassword(UserRequest("",password,mobile,"",""))) {
                    is NetworkResult.Success -> {
                        isLoading = false
                       passwordUpdateStatus = true
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