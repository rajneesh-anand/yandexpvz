package com.neo.yandexpvz.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.MAP_SCREEN
import com.neo.yandexpvz.PROFILE_SCREEN
import com.neo.yandexpvz.ui.theme.OrangeLightColor
import com.neo.yandexpvz.ui.theme.TextColor
import com.neo.yandexpvz.ui.theme.WhiteColor


@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val navItemList = listOf(
        BottomNavItem.HomeNav,
        BottomNavItem.GiftCardNav,
        BottomNavItem.LocationNav,
        BottomNavItem.ProfileNav,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var bottomNavVisibility by remember { mutableStateOf<Boolean>(true) }

    if (bottomNavVisibility) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.OrangeLightColor,
            modifier = Modifier.background(color = Color.White)
        ) {
            navItemList.forEach { screen ->
                BottomNavigationItem(
                    selected = navBackStackEntry?.destination?.route == screen.route,
                    icon = {

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colors.WhiteColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null
                            )
                        }







//                        Icon(
//                            painter = painterResource(id = screen.icon),
//                            contentDescription = null,
//                            tint= Color.White
////                            tint = if (navBackStackEntry?.destination?.route == screen.route) MaterialTheme.colors.OrangeDarkColor else LocalContentColor.current,
//                        )
                    },

                    onClick = {
//                        navController.navigate(screen.route) { launchSingleTop = true }
                        navController.navigate(screen.route) {
                            launchSingleTop = true
//                            popUpTo(0) { inclusive = true }
                        }

                    },
                    unselectedContentColor = MaterialTheme.colors.TextColor,
                )
            }
        }
    }

    //hide topBar
    when (navBackStackEntry?.destination?.route) {
        HOME_SCREEN -> {
            bottomNavVisibility = true

        }
        PROFILE_SCREEN -> {
            bottomNavVisibility = true
        }
        MAP_SCREEN -> {
            bottomNavVisibility = true
        }


        else -> {
            bottomNavVisibility = false

        }
    }



}