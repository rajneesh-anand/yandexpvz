package com.neo.yandexpvz.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.SIGNIN_SCREEN
import com.neo.yandexpvz.WELCOME_SCREEN
import com.neo.yandexpvz.repository.DataStoreRepository
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: DataStoreRepository,
    private val tokenManager: TokenManager
) : ViewModel() {


    private var _tokenValue:String? = null

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveOnBoardingState(completed = completed)
        }
    }


    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        _tokenValue = tokenManager.getToken()

        if (_tokenValue.isNullOrBlank()) {
            openAndPopUp(SIGNIN_SCREEN, WELCOME_SCREEN)
        } else {
            openAndPopUp(HOME_SCREEN, WELCOME_SCREEN)
        }
//
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.saveOnBoardingState(completed = completed)
//        }

    }



}