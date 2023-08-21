package com.neo.yandexpvz.screens.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.SIGNIN_SCREEN
import com.neo.yandexpvz.SPLASH_SCREEN
import com.neo.yandexpvz.WELCOME_SCREEN
import com.neo.yandexpvz.repository.DataStoreRepository
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    val showError = mutableStateOf(false)
    private var _tokenValue:String? = null
//
//    private val _startDestination: MutableState<String> = mutableStateOf(WELCOME_SCREEN)
//    val startDestination: State<String> = _startDestination

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        _tokenValue = tokenManager.getToken()
        showError.value = false

        viewModelScope.launch {
            dataStoreRepository.readOnBoardingState().collect { completed ->
                if (completed) {
                    if (_tokenValue.isNullOrBlank()) {
                        openAndPopUp(SIGNIN_SCREEN, SPLASH_SCREEN)
                    } else {
                        openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
                    }
                } else {
                    openAndPopUp(WELCOME_SCREEN, SPLASH_SCREEN)
                }
            }
        }
    }


}