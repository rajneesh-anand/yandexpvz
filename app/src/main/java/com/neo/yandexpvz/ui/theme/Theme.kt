package com.neo.yandexpvz.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable




private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

)


@Composable
fun YandexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {


    //   val colors = if (darkTheme) {
    //      DarkColorPalette
    // } else {
    LightColorPalette
    //   }

    MaterialTheme(
        colors = MaterialTheme.colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}