package com.neo.yandexpvz.screens.home

import com.neo.yandexpvz.GIFT_SCREEN
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.MAP_SCREEN
import com.neo.yandexpvz.PROFILE_SCREEN
import com.neo.yandexpvz.R


sealed class BottomNavItem(val tittle: String, val icon: Int, val route: String) {
    object HomeNav : BottomNavItem(
        tittle = "Home",
        icon = R.drawable.shop_icon,
        route = HOME_SCREEN
    )
    object GiftCardNav : BottomNavItem(
        tittle = "Gift Card",
        icon = R.drawable.gift_icon,
        route = GIFT_SCREEN
    )

    object LocationNav : BottomNavItem(
        tittle = "Location",
        icon = R.drawable.location,
        route = MAP_SCREEN
    )


    object ProfileNav : BottomNavItem(
        tittle = "Profile",
        icon = R.drawable.profile,
        route = PROFILE_SCREEN
    )
}
