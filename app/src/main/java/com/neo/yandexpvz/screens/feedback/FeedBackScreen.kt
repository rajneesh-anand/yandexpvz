package com.neo.yandexpvz.screens.feedback

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.neo.yandexpvz.FEEDBACK_SCREEN
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.PROFILE_INFO_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.common.components.MultiLineTextField
import com.neo.yandexpvz.components.CustomDefaultBtn
import com.neo.yandexpvz.components.ErrorSuggestion
import com.neo.yandexpvz.screens.profileinfo.createTemoraryFile
import com.neo.yandexpvz.ui.theme.OrangeColor
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.ui.theme.OrangeLightColor
import com.neo.yandexpvz.ui.theme.TextColor
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedBackScreen(
    openAndPopUp: (String,String) -> Unit,
    viewModel: FeedBackViewModel = hiltViewModel()
) {
    val coffeeDrinks = arrayOf("Предположение", "Жалоба", "Проблема с приложением", "Приветствие Примечание", "Другие")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }
    val context = LocalContext.current
    val messageErrorState = remember {
        mutableStateOf(false)
    }
    val result = remember { mutableStateOf<Bitmap?>(null) }
    val photoFile =  remember { mutableStateOf<File?>(null) }

    val loadImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()){
        val source = ImageDecoder.createSource(context.contentResolver, it!!)
        result.value= ImageDecoder.decodeBitmap(source)
        photoFile.value = createTemoraryFile(context.contentResolver.openInputStream(it),context)


    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 12.dp, end = 12.dp),
       horizontalAlignment = Alignment.CenterHorizontally,
       
        ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(modifier = Modifier.weight(0.3f)) {
                IconButton(
                    onClick = { openAndPopUp(HOME_SCREEN, FEEDBACK_SCREEN)},
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.OrangeLightColor,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = "Arrow Back"
                    )
                }
            }
            Box(modifier = Modifier.weight(0.7f)) {
                Text(
                    text = stringResource(id = R.string.feedbacks),
                    color = MaterialTheme.colors.OrangeDarkColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

            ){
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.feedback),
                contentDescription = "feedback image",

                modifier = Modifier
                    .size(168.dp)
                    .clip(RectangleShape)

            )
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = selectedText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(text = stringResource(R.string.message_category)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        trailingIconColor = MaterialTheme.colors.OrangeColor,
                        focusedBorderColor = MaterialTheme.colors.OrangeColor,
                        focusedLabelColor = MaterialTheme.colors.OrangeColor,
                        unfocusedBorderColor= MaterialTheme.colors.OrangeColor,
                        textColor = MaterialTheme.colors.TextColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    coffeeDrinks.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedText = item
                                expanded = false
                            }
                        ) {
                            Text(text = item)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            MultiLineTextField(
                placeholder = R.string.feedbacks_message,
                value = viewModel.message,
                label = R.string.message,
                minLines = 6,
                maxLines = 12,
                keyboardType = KeyboardType.Text,
                visualTransformation = VisualTransformation.None,
                errorState = messageErrorState,
                onChanged = { viewModel.onMessageChange(it) },
            )
            if (messageErrorState.value) {
                Spacer(modifier = Modifier.height(4.dp))
                ErrorSuggestion(stringResource(R.string.message_error))
            }
            Spacer(modifier = Modifier.height(16.dp))

            IconButton(onClick = { loadImage.launch("image/*") }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.camera_icon),
                        contentDescription = "change photo"
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Text(
                        text = stringResource(id = R.string.screenshot),
                        color=MaterialTheme.colors.OrangeColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            if(result.value != null) {
                result.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Profile Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(168.dp)
                            .clip(RectangleShape)

                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (viewModel.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colors.OrangeDarkColor)
            }
            CustomDefaultBtn(shapeSize = 4f, btnText = R.string.send_message) {
                val isMessageValid = viewModel.message.isNullOrBlank()
                messageErrorState.value = isMessageValid

                if (!isMessageValid) {

                    viewModel.sendFeedBackMessage(photoFile.value,selectedText)

                }
            }


        }

    }
}