package com.neo.yandexpvz.screens.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.PASSWORD_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.SIGNIN_SCREEN
import com.neo.yandexpvz.SIGNUP_SCREEN
import com.neo.yandexpvz.common.components.InternetConnectionError
import com.neo.yandexpvz.common.ext.isValidMobile
import com.neo.yandexpvz.common.ext.isValidPassword
import com.neo.yandexpvz.components.CustomDefaultBtn
import com.neo.yandexpvz.components.CustomMobileField
import com.neo.yandexpvz.components.CustomPasswordField
import com.neo.yandexpvz.components.ErrorSuggestion
import com.neo.yandexpvz.internet.ConnectionState
import com.neo.yandexpvz.internet.connectivityState
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeColor
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.TextColor

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@Composable
fun SignInScreen(
    openAndPopUp: (String, String ) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {

    val networkConnectivity by connectivityState()
    val uiState by viewModel.uiState

    var checkBox by remember {
        mutableStateOf(false)
    }
    val mobileErrorState = remember {
        mutableStateOf(false)
    }
    val passwordErrorState = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.userLoginStatus) {
        if(viewModel.userLoginStatus) {
            openAndPopUp(HOME_SCREEN, SIGNIN_SCREEN)
        }
    }

    if(networkConnectivity == ConnectionState.Unavailable) {
        InternetConnectionError()
    }else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = R.drawable.pup),
                contentDescription = "puppy"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.signin_with_mobile),
                color = MaterialTheme.colors.BlueText,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(32.dp))

            CustomMobileField(
                value = uiState.mobile,
                label = R.string.mobile_label,
                mask = "+7 XXX XXX XX-XX",
                maskNumber = 'X',
                errorState = mobileErrorState,
                onChanged = { viewModel.onMobileChange(it) },
            )
            if (mobileErrorState.value) {
                Spacer(modifier = Modifier.height(4.dp))
                ErrorSuggestion(stringResource(R.string.mobile_error))
            }

            Spacer(modifier = Modifier.height(16.dp))
            CustomPasswordField(
                value = uiState.password,
                label = R.string.password_label,
                errorState = passwordErrorState,
                viewModel::onPasswordChange
            )
            if (passwordErrorState.value) {
                Spacer(modifier = Modifier.height(4.dp))
                ErrorSuggestion(stringResource(R.string.password_error))
            }
            Spacer(modifier = Modifier.height(10.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {


                Text(
                    text = "изменить пароль !",
                    color = MaterialTheme.colors.TextColor,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier.clickable {
                        openAndPopUp(PASSWORD_SCREEN, SIGNIN_SCREEN)
                    }
                )

            }
            CustomDefaultBtn(shapeSize = 4f, btnText = R.string.sign_in) {

                val isMobileValid = uiState.mobile.isValidMobile()
                mobileErrorState.value = !isMobileValid

                val isPassValid = uiState.password.isValidPassword()
                passwordErrorState.value = !isPassValid

                if (isMobileValid && isPassValid) {
                    viewModel.onSignInClick()
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            if (uiState.errorText.isNotEmpty()) {
                Text(text = uiState.errorText, color = Color.Red)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_account) + "  ",
                        color = MaterialTheme.colors.BlueText,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = stringResource(R.string.sign_up) + " ",
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        color = MaterialTheme.colors.OrangeColor,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            openAndPopUp(SIGNUP_SCREEN, SIGNIN_SCREEN)
                        }
                    )
                }


                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colors.OrangeDarkColor)
                }


            }


        }
    }
}


