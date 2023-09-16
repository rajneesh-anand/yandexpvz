package com.neo.yandexpvz.screens.profile


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
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

    fun onNavItemClick(restartApp: (String) -> Unit, screen: String) {
        if(screen == "SIGNIN_SCREEN"){
            tokenManager.deleteToken()
            restartApp(screen)
        }else{
            restartApp(screen)
        }

    }
}