package  com.neo.yandexpvz.components

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neo.yandexpvz.R
import com.neo.yandexpvz.ui.theme.OrangeColor
import com.neo.yandexpvz.ui.theme.TextColor


@Composable
fun CustomMobileField(
    value: String,
    label: Int,
    mask: String = "XXX XXX XX-XX",
    maskNumber: Char = 'X',
    errorState: MutableState<Boolean>,
    onChanged: (String) -> Unit,
) {
    OutlinedTextField(

        value = value,
        onValueChange = { it ->
           onChanged(it.take(mask.count { it == maskNumber }))
        },
        placeholder = { Text(text = stringResource(R.string.mobile_placeholder), color = Color.LightGray, fontSize = 18.sp) },
        label = { Text(text = stringResource(label)) },
        shape = RoundedCornerShape(1.dp),
        leadingIcon = { Icon(painter = painterResource(R.drawable.mobile), contentDescription = "Mobile", tint = Color.Unspecified,) },

        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            trailingIconColor = if (value.isNotEmpty()) MaterialTheme.colors.OrangeColor else MaterialTheme.colors.TextColor,
            cursorColor = MaterialTheme.colors.OrangeColor,
            focusedBorderColor = MaterialTheme.colors.OrangeColor,
            focusedLabelColor = MaterialTheme.colors.OrangeColor,
            textColor = MaterialTheme.colors.TextColor
        ),

        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation(mask, maskNumber),
        isError = errorState.value,
        modifier = Modifier
            .fillMaxWidth()
    )
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString

            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskNumber != other.maskNumber) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}




