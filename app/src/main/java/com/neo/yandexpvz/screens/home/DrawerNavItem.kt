package com.neo.yandexpvz.screens.home
import com.neo.yandexpvz.FEEDBACK_SCREEN
import com.neo.yandexpvz.GIFT_SCREEN
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.MAP_SCREEN
import com.neo.yandexpvz.PROFILE_INFO_SCREEN
import com.neo.yandexpvz.PROFILE_SCREEN
import com.neo.yandexpvz.R
import com.neo.yandexpvz.SIGNIN_SCREEN


sealed class DrawerNavItem(val tittle: String, val icon: Int, val route: String) {
    object HomeNav : DrawerNavItem(
        tittle = "Home",
        icon = R.drawable.shop_icon,
        route = HOME_SCREEN
    )
    object GiftCardNav : DrawerNavItem(
        tittle = "Подарочная карта",
        icon = R.drawable.gift_icon,
        route = GIFT_SCREEN
    )

    object LocationNav : DrawerNavItem(
        tittle = "Location",
        icon = R.drawable.location,
        route = MAP_SCREEN
    )
    object ProfileInfoNav : DrawerNavItem(
        tittle = "Информация профиля",
        icon = R.drawable.avatar,
        route = PROFILE_INFO_SCREEN
    )


    object SupportNav : DrawerNavItem(
        tittle = "Поддержка",
        icon = R.drawable.help,
        route = PROFILE_SCREEN
    )

    object FeedbackNav : DrawerNavItem(
        tittle = "Отзывы",
        icon = R.drawable.email,
        route = FEEDBACK_SCREEN
    )

    object SignOutNav : DrawerNavItem(
        tittle = "Выход",
        icon = R.drawable.logout,
        route = SIGNIN_SCREEN
    )
}
