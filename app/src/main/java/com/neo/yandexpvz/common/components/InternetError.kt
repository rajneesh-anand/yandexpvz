package com.neo.yandexpvz.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neo.yandexpvz.R
import com.neo.yandexpvz.ui.theme.BlueText
import com.neo.yandexpvz.ui.theme.OrangeColor

@Composable
fun InternetConnectionError(onRetry: () -> Unit){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(R.drawable.no_internet),
            contentDescription = "No Internet",
           modifier = Modifier.size(240.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Проверьте подключение к Интернету !")
        Spacer(modifier = Modifier.height(16.dp))
        TextButton( onClick = { onRetry() }){
            Icon(
                painterResource(R.drawable.retry),
                contentDescription = "Retry",
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = Color.Unspecified
            )
            Spacer(modifier=Modifier.width(8.dp))
            Text(text= stringResource(id = R.string.retry), color = MaterialTheme.colors.OrangeColor)
        }
    }
}