package com.neo.yandexpvz.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.neo.yandexpvz.GIFT_SCREEN
import com.neo.yandexpvz.HOME_SCREEN
import com.neo.yandexpvz.ITEM_SCREEN
import com.neo.yandexpvz.MAP_SCREEN
import com.neo.yandexpvz.PROFILE_SCREEN
import com.neo.yandexpvz.ui.theme.OrangeLightColor
import com.neo.yandexpvz.ui.theme.TextColor


@Composable
fun BottomNavigationBar(
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    val coroutineScope = rememberCoroutineScope()

    val navItemList = listOf(
        BottomNavItem.HomeNav,
        BottomNavItem.DiscountNav,
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
                    icon ={
                        Column( horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier)
                        {
                            Image(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null
                            )
                            Text(
                                text = screen.tittle,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    },
                    // Sidebar Drawer
//                    onClick = {
//                        coroutineScope.launch {
//                        scaffoldState.drawerState.open()
//                         } },

                    onClick = {
                           navController.navigate(screen.route) {
                                launchSingleTop = true
                                popUpTo(0) { inclusive = true }
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
        GIFT_SCREEN -> {
            bottomNavVisibility = true
        }
        ITEM_SCREEN -> {
            bottomNavVisibility = true
        }
        else -> {
            bottomNavVisibility = false
        }
    }



}