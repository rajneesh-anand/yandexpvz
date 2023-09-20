package com.neo.yandexpvz.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.neo.yandexpvz.screens.home.DrawerNavItem
import com.neo.yandexpvz.ui.theme.OrangeLightColor


@Composable
fun NavigationDrawerHeader() {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.OrangeLightColor)
            .fillMaxWidth()
            .height(180.dp)
            .padding(32.dp)
    ) {

        Text(text = "Hello")

    }
}


@Composable
fun NavigationDrawerBody(navController: NavController)
{
    val navItemList = listOf(
        DrawerNavItem.HomeNav,
        DrawerNavItem.GiftCardNav,
        DrawerNavItem.LocationNav,
        DrawerNavItem.SupportNav,
    )

    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        items(navItemList) {
            NavigationItemRow(item = it, navController)
        }

    }
}

@Composable
fun NavigationItemRow(item: DrawerNavItem, navController: NavController) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(item.route) {
                    launchSingleTop = true
                }
            }
          .padding(all = 16.dp)
    ) {

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = null
        )


        Spacer(modifier = Modifier.width(18.dp))

        Text(text = item.tittle)


    }
}
