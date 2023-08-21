package com.neo.yandexpvz.common.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.neo.yandexpvz.ui.theme.OrangeDarkColor


@Composable
fun ActionToolbar(
    @StringRes title: Int,
    @DrawableRes endActionIcon: Int,
    modifier: Modifier,
    profileAction: () -> Unit,
    signOutAction :() -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(title)) },
        backgroundColor = MaterialTheme.colors.OrangeDarkColor,
        actions = {
            Row() {
                Box(modifier) {
                    IconButton(onClick = profileAction) {
                        Icon(painter = painterResource(endActionIcon), contentDescription = "Action")
                    }
                }
//                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier) {
                    IconButton(onClick = signOutAction) {
                        Icon(painter = painterResource(endActionIcon), contentDescription = "Action")
                    }
                }
            }

        }
    )
}
