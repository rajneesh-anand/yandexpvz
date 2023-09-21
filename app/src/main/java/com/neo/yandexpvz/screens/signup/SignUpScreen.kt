package com.neo.yandexpvz.screens.signup

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.SIGNIN_SCREEN
import com.neo.yandexpvz.SIGNUP_SCREEN
import com.neo.yandexpvz.common.components.CustomEmailField
import com.neo.yandexpvz.common.components.CustomTextField
import com.neo.yandexpvz.common.ext.isValidEmail
import com.neo.yandexpvz.common.ext.isValidMobile
import com.neo.yandexpvz.common.ext.isValidPassword
import com.neo.yandexpvz.components.CustomDefaultBtn
import com.neo.yandexpvz.components.CustomMobileField
import com.neo.yandexpvz.components.CustomPasswordField
import com.neo.yandexpvz.components.DefaultBackArrow
import com.neo.yandexpvz.components.ErrorSuggestion
import com.neo.yandexpvz.model.Highlight
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.WarningColor


@Composable
fun SignUpScreen (
    openAndPopUp: (String,String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState
    val mobileErrorState = remember {
        mutableStateOf(false)
    }
    val passwordErrorState = remember {
        mutableStateOf(false)
    }
    val emailErrorState = remember {
        mutableStateOf(false)
    }
    val nameErrorState = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.userRegisterStatus) {
        if (viewModel.userRegisterStatus) {
            openAndPopUp(HOME_SCREEN, SIGNUP_SCREEN)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 8.dp)
            .verticalScroll(rememberScrollState()),
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
                        openAndPopUp(SIGNIN_SCREEN,SIGNUP_SCREEN)
                }
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.pup),
            contentDescription = "puppy"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.sign_up_with_mobile),
            color = MaterialTheme.colors.BlueText,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )

//        Text(
//            text = "Complete your details or continue\nwith social media.",
//            color = MaterialTheme.colors.TextColor,
//            textAlign = TextAlign.Center
//        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomTextField(
            placeholder = R.string.name_placeholder,
            value=uiState.name,
            label=R.string.name_label,
            trailingIcon= R.drawable.user,
            keyboardType = KeyboardType.Text,
            visualTransformation = VisualTransformation.None,
            errorState = nameErrorState,
            onChanged = { viewModel.onNameChange(it)},
        )
        if (nameErrorState.value) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorSuggestion(stringResource(R.string.name_error))
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomMobileField(
            value =  uiState.mobile,
            label = R.string.mobile_label,
            mask= "+7 XXX XXX XX-XX",
            maskNumber = 'X',
            errorState = mobileErrorState,
            onChanged = { viewModel.onMobileChange(it)}
            )
        if (mobileErrorState.value) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorSuggestion(stringResource(R.string.mobile_error))
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomEmailField(
            value = uiState.email,
            label = R.string.email_label,
            errorState = emailErrorState ,
            onChanged ={ viewModel.onEmailChange(it)}
        )
        if (emailErrorState.value) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorSuggestion(stringResource(R.string.email_error))
        }
        Spacer(modifier = Modifier.height(10.dp))
        CustomPasswordField(
            value = uiState.password,
            label = R.string.password_label,
            errorState =passwordErrorState,
            onChanged = { viewModel.onPasswordChange(it)}
        )

        if (passwordErrorState.value) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorSuggestion(stringResource(R.string.password_error))
        }
        Spacer(modifier = Modifier.height(16.dp))
//        AgreeConditionComponent(modifier = Modifier)
        HighlightedText(
            text = stringResource(id = R.string.disclaimer),
            highlights = listOf(
                Highlight(
                    text = stringResource(id = R.string.privacy_policy),
                    data = "https://yasha64.github.io/privacy-policy.html",
                    onClick = { link ->
                        context.startActivity( Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(link)
                    ))
                    }
                ),
                Highlight(
                    text = stringResource(id = R.string.terms_of_use),
                    data = "https://yasha64.github.io/privacy-policy.html",
                    onClick = { link ->
                        context.startActivity( Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(link)))
                    }
                )
            )
        )
        CustomDefaultBtn(shapeSize = 4f, btnText = R.string.sign_up) {

            val isMobileValid = uiState.mobile.isValidMobile()
            mobileErrorState.value = !isMobileValid

            val isPassValid = uiState.password.isValidPassword()
            passwordErrorState.value = !isPassValid


            val isNameValid = uiState.name.isNullOrBlank()
            nameErrorState.value = isNameValid


            val isEmailValid = uiState.email.isValidEmail()
            emailErrorState.value = !isEmailValid

            if (isMobileValid && isPassValid &&  isEmailValid && !isNameValid ) {
                viewModel.onSignUpClick()
            }
        }
        if (uiState.errorText.isNotEmpty()) {
            Text(text = uiState.errorText, color = MaterialTheme.colors.WarningColor)
        }

        if (viewModel.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.OrangeDarkColor)
        }
    }
}


//
//@Composable
//fun AgreeConditionComponent(
//    modifier: Modifier
//) {
//    val context = LocalContext.current
//    val hyperlinkText1 = "условиями"
//    val hyperlinkText2 = "политикой конфиденциальности"
//    val annotatedString = buildAnnotatedString {
//        append("Создавая учетную запись, вы соглашаетесь с нашими")
//        append(" ")
//        val start = length
//        val end = length + hyperlinkText1.length
//        addStringAnnotation(tag = "условиями", annotation = "", start = start, end = end)
//        withStyle(
//            style = SpanStyle(
//                textDecoration = TextDecoration.Underline,
//                color = MaterialTheme.colors.BlueText,
//            ),
//        ) {
//            append(hyperlinkText1)
//        }
//        append(" ")
//        append("и")
//        append(" ")
//        val s = length
//        val e = length + hyperlinkText2.length
//        addStringAnnotation(tag = "политикой конфиденциальности", annotation = "", start = s, end = e)
//        withStyle(
//            style = SpanStyle(
//                textDecoration = TextDecoration.Underline,
//                color = MaterialTheme.colors.BlueText,
//            ),
//        ) {
//            append(hyperlinkText2)
//        }
//    }
//    Row(
//        modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Spacer(modifier = Modifier.width(4.dp))
//        ClickableText(
//            text = annotatedString,
//            style = TextStyle(
//                fontSize = 12.sp,
//            ),
//            onClick = { offset ->
//                annotatedString.getStringAnnotations(tag = "условиями", start = offset, end = offset)
//                    .firstOrNull()
//                    ?.let { context.startActivity( Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse("https://www.google.com")
//                    )) }
//            }
//        )
//    }
//}
//
//



@Composable
fun HighlightedText(
    text: String,
    highlights: List<Highlight>,
    modifier: Modifier = Modifier
) {
    data class TextData(
        val text: String,
        val tag: String? = null,
        val data: String? = null,
        val onClick: ((data: AnnotatedString.Range<String>) -> Unit)? = null
    )

    val textData = mutableListOf<TextData>()
    if (highlights.isEmpty()) {
        textData.add(
            TextData(
                text = text
            )
        )
    } else {
        var startIndex = 0
        highlights.forEachIndexed { i, link ->
            val endIndex = text.indexOf(link.text)
            if (endIndex == -1) {
                throw Exception("Highlighted text mismatch")
            }
            textData.add(
                TextData(
                    text = text.substring(startIndex, endIndex)
                )
            )
            textData.add(
                TextData(
                    text = link.text,
                    tag = "${link.text}_TAG",
                    data = link.data,
                    onClick = {
                        link.onClick(it.item)
                    }
                )
            )
            startIndex = endIndex + link.text.length
            if (i == highlights.lastIndex && startIndex < text.length) {
                textData.add(
                    TextData(
                        text = text.substring(startIndex, text.length)
                    )
                )
            }
        }
    }

    val annotatedString = buildAnnotatedString {
        textData.forEach { linkTextData ->
            if (linkTextData.tag != null && linkTextData.data != null) {
                pushStringAnnotation(
                    tag = linkTextData.tag,
                    annotation = linkTextData.data,
                )
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.BlueText,
                        textDecoration = TextDecoration.Underline,
                    ),
                ) {
                    append(linkTextData.text)
                }
                pop()
            } else {
                append(linkTextData.text)
            }
        }
    }
    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
//            color = MaterialTheme.colors.BlueText,
            textAlign = TextAlign.Start
        ),
        onClick = { offset ->
            textData.forEach { annotatedStringData ->
                if (annotatedStringData.tag != null && annotatedStringData.data != null) {
                    annotatedString.getStringAnnotations(
                        tag = annotatedStringData.tag,
                        start = offset,
                        end = offset,
                    ).firstOrNull()?.let {
                        annotatedStringData.onClick?.invoke(it)
                    }
                }
            }
        },
        modifier = modifier
    )
}