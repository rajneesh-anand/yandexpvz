package com.neo.yandexpvz.screens.profileinfo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.PROFILE_INFO_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.components.CustomDefaultBtn
import com.neo.yandexpvz.components.DefaultBackArrow
import com.neo.yandexpvz.components.ErrorSuggestion
import com.neo.yandexpvz.ui.theme.OrangeColor
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.TextColor
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date

@Composable
@ExperimentalMaterialApi
fun ProfileInfoScreen(
    openAndPopUp: (String,String) -> Unit,
    viewModel: ProfileInfoViewModel = hiltViewModel()

) {
    val context = LocalContext.current
    val nameErrorState = remember {
        mutableStateOf(false)
    }


    val result = remember { mutableStateOf<Bitmap?>(null) }
    val photoFile =  remember { mutableStateOf<File?>(null) }

    val loadImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
        val source = ImageDecoder.createSource(context.contentResolver, it!!)
        result.value= ImageDecoder.decodeBitmap(source)
        photoFile.value = createTemoraryFile(context.contentResolver.openInputStream(it),context)
        Log.d("PROFILE PHOTO 1","${photoFile.value}")

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(0.2f)) {
                DefaultBackArrow {
                    openAndPopUp(HOME_SCREEN, PROFILE_INFO_SCREEN)
                }
            }
            Box(modifier = Modifier.weight(0.9f)) {
                Text(
                    text = stringResource(id = R.string.edit_profile),
                    color = MaterialTheme.colors.OrangeDarkColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        if(result.value != null){
                result.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(168.dp)
                            .clip(CircleShape)

                    )
                }
            }else{
                AsyncImage(
                    model = viewModel.userImage,
                    contentDescription = "profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(168.dp)
                        .clip(CircleShape)

                )
            }
            IconButton(onClick = { loadImage.launch("image/*") }) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.camera_icon),
                        contentDescription = "change photo"
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Text(text = stringResource(id = R.string.change_photo))
                }
            }


        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { username -> viewModel.updateName(username) },
            label = { Text(stringResource(R.string.name_label)) },
            singleLine = true,
            shape = RoundedCornerShape(1.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colors.OrangeColor,
                focusedBorderColor = MaterialTheme.colors.OrangeColor,
                focusedLabelColor = MaterialTheme.colors.OrangeColor,
                unfocusedBorderColor = MaterialTheme.colors.OrangeColor,
                unfocusedLabelColor = MaterialTheme.colors.OrangeColor,
                textColor = MaterialTheme.colors.TextColor
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "text", tint = Color.Unspecified
                )},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = nameErrorState.value,
            modifier = Modifier
                .fillMaxWidth()
        )

        if (nameErrorState.value ) {
            Spacer(modifier = Modifier.height(4.dp))
            ErrorSuggestion(stringResource(R.string.name_error))
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = viewModel.mobile,
            onValueChange = { },
            readOnly = true,
            label = { Text(stringResource(R.string.mobile_label)) },
            singleLine = true,
            shape = RoundedCornerShape(1.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colors.OrangeColor,
                focusedBorderColor = MaterialTheme.colors.OrangeColor,
                focusedLabelColor = MaterialTheme.colors.OrangeColor,
                unfocusedBorderColor = MaterialTheme.colors.OrangeColor,
                unfocusedLabelColor = MaterialTheme.colors.OrangeColor,
                textColor = MaterialTheme.colors.TextColor
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.mobile),
                    contentDescription = "text", tint = Color.Unspecified
                )},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation = PhoneVisualTransformation(
                "+7 000 000 00-00",
                '0'
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = {  },
            readOnly = true,
            label = { Text(stringResource(R.string.email_label)) },
            singleLine = true,
            shape = RoundedCornerShape(1.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = MaterialTheme.colors.OrangeColor,
                focusedBorderColor = MaterialTheme.colors.OrangeColor,
                focusedLabelColor = MaterialTheme.colors.OrangeColor,
                unfocusedBorderColor = MaterialTheme.colors.OrangeColor,
                unfocusedLabelColor = MaterialTheme.colors.OrangeColor,
                textColor = MaterialTheme.colors.TextColor
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.email),
                    contentDescription = "text", tint = Color.Unspecified
                )},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        CustomDefaultBtn(shapeSize = 4f, btnText = R.string.update_profile) {
            val isNameValid = viewModel.name.isNullOrBlank()
            nameErrorState.value = isNameValid

            if (!isNameValid) {
                Log.d("PROFILE PHOTO","${photoFile.value}")
                viewModel.updateProfile(photoFile.value)

            }
        }
        if (viewModel.isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.OrangeDarkColor)
        }
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val image = File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}

fun createTemoraryFile(iStream: InputStream?, context:Context): File {
    val inputStream = iStream
    val file = context.createImageFile()
//
//    val f = createTempFile(
//        directory = context.cacheDir
//    )
    if (inputStream != null) {
        inputStream.copyTo(file.outputStream())
    }
    return file
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
