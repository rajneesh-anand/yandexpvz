package com.neo.yandexpvz.screens.profileinfo

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.R
import com.neo.yandexpvz.common.snackbar.SnackbarManager
import com.neo.yandexpvz.repository.UserRepository
import com.neo.yandexpvz.utils.NetworkResult
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ProfileInfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
): ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var errorText: String by mutableStateOf("")
    var userImage: String = tokenManager.getUserImage().toString()
    var name by mutableStateOf(tokenManager.getUserName().toString())
        private set
    var mobile by mutableStateOf(tokenManager.getUserMobile().toString())
        private set
    var email by mutableStateOf(tokenManager.getUserEmail().toString())
        private set


    fun updateName(input: String) {
        name = input
    }

    fun updateProfile(file: File?){
        isLoading = true
        val mobile = tokenManager.getUserMobile().toString()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response =
                    userRepository.updateProfile(file, name, mobile)) {
                    is NetworkResult.Success -> {
                        tokenManager.updateUser(response.data!!.image_url,response.data!!.name)
                        isLoading = false
                        SnackbarManager.showMessage(R.string.profile_updated)
                    }
                    is NetworkResult.Error -> {
                        isLoading = false
                    }
                    else -> {
                        isLoading = false
                    }
                }

            } catch (exception: Exception) {
                isLoading = false
                Log.d("Yandex-PVZ", ": ${exception.message.toString()}")
            }
        }
    }
}