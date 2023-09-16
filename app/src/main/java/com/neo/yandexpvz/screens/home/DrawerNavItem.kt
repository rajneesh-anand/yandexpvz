package com.neo.yandexpvz.screens.home
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
        tittle = "Gift Card",
        icon = R.drawable.gift_icon,
        route = GIFT_SCREEN
    )

    object LocationNav : DrawerNavItem(
        tittle = "Location",
        icon = R.drawable.location,
        route = MAP_SCREEN
    )
    object ProfileInfoNav : DrawerNavItem(
        tittle = "Profile Info",
        icon = R.drawable.location,
        route = PROFILE_INFO_SCREEN
    )


    object ProfileNav : DrawerNavItem(
        tittle = "Profile",
        icon = R.drawable.profile,
        route = PROFILE_SCREEN
    )

    object SignOutNav : DrawerNavItem(
        tittle = "Sign Out",
        icon = R.drawable.profile,
        route = SIGNIN_SCREEN
    )
}
