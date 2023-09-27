package com.neo.yandexpvz.screens.feedback

import android.util.Log
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
class FeedBackViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
) : ViewModel() {

    var isLoading: Boolean by mutableStateOf(false)
    var errorText: String by mutableStateOf("")
    var message:   String  by mutableStateOf("")

    fun onMessageChange(input: String) {
        message = input
    }


    fun sendFeedBackMessage(file: File?, category:String){
        isLoading = true
        val mobile = tokenManager.getUserMobile().toString()
        val name = tokenManager.getUserName().toString()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val response =
                    userRepository.sendFeedBackMessage(file, name, mobile, message, category)) {
                    is NetworkResult.Success -> {
                        isLoading = false
                        SnackbarManager.showMessage(R.string.sent_message)
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
                Log.d("Yandex-PVZ", ": ${exception.message.toString()}")
                errorText = exception.message.toString()
            }
        }
    }


}