package com.neo.yandexpvz.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.yandexpvz.R
import com.neo.yandexpvz.ui.theme.OrangeColor
import com.neo.yandexpvz.ui.theme.TextColor


@Composable
fun CustomEmailField(
    value: String,
    label: Int,
    errorState: MutableState<Boolean>,
    onChanged: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = { onChanged(it) },
        placeholder = { Text(text = stringResource(R.string.email_placeholder), color = Color.LightGray, fontSize = 18.sp) },
        label = { Text(text = stringResource(label)) },
        shape = RoundedCornerShape(1.dp),
        leadingIcon = { Icon(painter = painterResource(R.drawable.email), contentDescription = "Email", tint = Color.Unspecified,) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            trailingIconColor = if (value.isNotEmpty()) MaterialTheme.colors.OrangeColor else MaterialTheme.colors.TextColor,
            cursorColor = MaterialTheme.colors.OrangeColor,
            focusedBorderColor = MaterialTheme.colors.OrangeColor,
            focusedLabelColor = MaterialTheme.colors.OrangeColor,
            textColor = MaterialTheme.colors.TextColor
        ),
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = errorState.value,
        modifier = Modifier
            .fillMaxWidth()
    )
}
