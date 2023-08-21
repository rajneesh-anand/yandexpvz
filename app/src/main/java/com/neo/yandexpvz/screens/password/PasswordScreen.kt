package com.neo.yandexpvz.screens.password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neo.yandexpvz.PASSWORD_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.SIGNIN_SCREEN
import com.neo.yandexpvz.common.ext.isValidMobile
import com.neo.yandexpvz.common.ext.isValidPassword
import com.neo.yandexpvz.components.CustomDefaultBtn
import com.neo.yandexpvz.components.CustomMobileField
import com.neo.yandexpvz.components.CustomPasswordField
import com.neo.yandexpvz.components.DefaultBackArrow
import com.neo.yandexpvz.components.ErrorSuggestion
import com.neo.yandexpvz.ui.theme.BlueText


@Composable
fun PasswordScreen (
    openAndPopUp: (String,String) -> Unit,
    viewModel: PasswordViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    val mobileErrorState = remember {
        mutableStateOf(false)
    }
    val passwordErrorState = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(viewModel.passwordUpdateStatus) {
        if (viewModel.passwordUpdateStatus) {
            openAndPopUp(SIGNIN_SCREEN, PASSWORD_SCREEN)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(modifier = Modifier.weight(0.7f)) {
                DefaultBackArrow {
                    openAndPopUp(SIGNIN_SCREEN, PASSWORD_SCREEN)
                }
            }
//            Box(modifier = Modifier.weight(1.0f)) {
//                Text(
//                    text = "изменить пароль",
//                    color = MaterialTheme.colors.TextColor,
//                    fontSize = 18.sp
//                )
//            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.pup),
            contentDescription = "puppy"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.password_change),
            color = MaterialTheme.colors.BlueText,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomMobileField(
            value =  uiState.mobile,
            label = R.string.mobile_label,
            mask= "+7 000-000-00-00",
            maskNumber = '0',
            errorState = mobileErrorState,
            onChanged = { viewModel.onMobileChange(it)}
        )
        if (mobileErrorState.value) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorSuggestion(stringResource(R.string.mobile_error))
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomPasswordField(
            value = uiState.password,
            label = R.string.password_label_new,
            errorState =passwordErrorState,
            onChanged = { viewModel.onPasswordChange(it)}
        )

        if (passwordErrorState.value) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorSuggestion(stringResource(R.string.password_error))
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomDefaultBtn(shapeSize = 4f, btnText = R.string.update_password) {

            val isMobileValid = uiState.mobile.isValidMobile()
            mobileErrorState.value = !isMobileValid

            val isPassValid = uiState.password.isValidPassword()
            passwordErrorState.value = !isPassValid

            if (isMobileValid && isPassValid ) {
                viewModel.onUpdatePasswordClick()
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.errorText.isNotEmpty()) {
            Text(text = uiState.errorText, color = Color.Red)
        }

        if (viewModel.isLoading) {
            CircularProgressIndicator()
        }


    }




}


