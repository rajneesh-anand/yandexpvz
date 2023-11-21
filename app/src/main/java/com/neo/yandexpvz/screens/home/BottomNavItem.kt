package com.neo.yandexpvz.screens.home

import com.neo.yandexpvz.GIFT_SCREEN
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.ITEM_SCREEN
import com.neo.yandexpvz.MAP_SCREEN
import com.neo.yandexpvz.PROFILE_SCREEN
import com.neo.yandexpvz.R


sealed class BottomNavItem(val tittle: String, val icon: Int, val route: String) {
    object HomeNav : BottomNavItem(
        tittle = "Продукты",
        icon = R.drawable.shop_icon,
        route = HOME_SCREEN
    )
    object GiftCardNav : BottomNavItem(
        tittle = "Дары",
        icon = R.drawable.gift_icon,
        route = GIFT_SCREEN
    )

    object DiscountNav : BottomNavItem(
        tittle = "Скидка",
        icon = R.drawable.deal,
        route = ITEM_SCREEN
    )

    object LocationNav : BottomNavItem(
        tittle = "Расположение",
        icon = R.drawable.location,
        route = MAP_SCREEN
    )


    object ProfileNav : BottomNavItem(
        tittle = "Профиль",
        icon = R.drawable.profile,
        route = PROFILE_SCREEN
    )
}
