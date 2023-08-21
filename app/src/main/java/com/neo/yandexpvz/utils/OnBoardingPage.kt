package com.neo.yandexpvz.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.res.stringResource
import com.neo.yandexpvz.R

sealed class OnBoardingPage(
    @DrawableRes
    val welcomeImage: Int,
    val image: Int,
    val title: Int,
    val description: Int
) {
    object First : OnBoardingPage(
        welcomeImage = R.drawable.welcome,
        image = R.drawable.splash_1,
        title =  R.string.welcome_1_title,
        description =  R.string.welcome_1_desc
    )

    object Second : OnBoardingPage(
        welcomeImage = R.drawable.welcome,
        image = R.drawable.splash_2,
        title =  R.string.welcome_2_title,
        description =  R.string.welcome_2_desc
    )

    object Third : OnBoardingPage(
        welcomeImage = R.drawable.welcome,
        image = R.drawable.splash_3,
        title =  R.string.welcome_3_title,
        description =  R.string.welcome_3_desc
    )
}
