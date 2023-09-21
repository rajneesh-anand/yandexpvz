package com.neo.yandexpvz

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.neo.yandexpvz.common.components.PermissionDialog
import com.neo.yandexpvz.common.components.RationaleDialog
import com.neo.yandexpvz.common.snackbar.SnackbarManager
import com.neo.yandexpvz.screens.home.HomeScreen
import com.neo.yandexpvz.screens.product.ProductScreen
import com.neo.yandexpvz.screens.signin.SignInScreen
import com.neo.yandexpvz.screens.signup.SignUpScreen
import com.neo.yandexpvz.ui.theme.YandexTheme
import kotlinx.coroutines.CoroutineScope
import com.neo.yandexpvz.screens.gift.GiftScreen
import com.neo.yandexpvz.screens.home.BottomNavigationBar
import com.neo.yandexpvz.screens.info.InfoScreen
import com.neo.yandexpvz.screens.map.MapScreen
import com.neo.yandexpvz.screens.password.PasswordScreen
import com.neo.yandexpvz.screens.profile.ProfileScreen
import com.neo.yandexpvz.screens.profileinfo.ProfileInfoScreen
import com.neo.yandexpvz.screens.redeem.RedeemScreen
import com.neo.yandexpvz.screens.splash.SplashScreen
import com.neo.yandexpvz.screens.welcome.WelcomeScreen
import com.neo.yandexpvz.ui.theme.OrangeDarkColor
import com.neo.yandexpvz.utils.Constants.USER_NOTIFICATION_TOPIC

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun YandexApp() {
    YandexTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }
        subscribeTopics()
        Surface() {
            val appState = rememberAppState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData,
                                backgroundColor = Color.White,
                                contentColor = MaterialTheme.colors.OrangeDarkColor,

                            )
                        }
                    )
                },
                scaffoldState = appState.scaffoldState,
//                drawerGesturesEnabled = appState.scaffoldState.drawerState.isOpen,
//                drawerContent = {
//                    NavigationDrawerHeader()
//                    NavigationDrawerBody(appState.navController)
//                },
                bottomBar = {
                        BottomNavigationBar(appState.navController, appState.scaffoldState,)
                }
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    yandexAppGraph(appState)
                }
            }
        }
    }
}



@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        YandexAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}


@ExperimentalMaterialApi
fun NavGraphBuilder.yandexAppGraph(appState: YandexAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(WELCOME_SCREEN) {
        WelcomeScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGNIN_SCREEN) {
        SignInScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGNUP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }


    composable(PASSWORD_SCREEN) {
        PasswordScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(HOME_SCREEN) {
        HomeScreen(
            openScreen = { route -> appState.navigate(route) },

        )
    }
    composable(PROFILE_SCREEN) {
        ProfileScreen(
            restartApp = { route -> appState.clearAndNavigate(route) })
    }
    composable(PROFILE_INFO_SCREEN) {
        ProfileInfoScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })

    }
    composable(INFO_SCREEN) {
        InfoScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
    composable(MAP_SCREEN) {
        MapScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(GIFT_SCREEN) {
        GiftScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(
        route = "$PRODUCT_SCREEN$PRODUCT_ID_ARG",
        arguments = listOf(navArgument(PRODUCT_ID) { defaultValue = PRODUCT_DEFAULT_ID })
    ) {
        ProductScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            productId = it.arguments?.getString(PRODUCT_ID) ?: PRODUCT_DEFAULT_ID

        )
    }
    composable(
        route = "$REDEEM_SCREEN$REDEEM_ID_ARG",
        arguments = listOf(navArgument(REDEEM_ID) { defaultValue = REDEEM_DEFAULT_ID })
    ) {
        RedeemScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            redeemId = it.arguments?.getString(REDEEM_ID) ?: REDEEM_DEFAULT_ID

        )
    }
}



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

@Composable
fun subscribeTopics() {
//    val context = LocalContext.current

    Firebase.messaging.subscribeToTopic(USER_NOTIFICATION_TOPIC)
        .addOnCompleteListener { task ->
            var msg = "Subscribed"
            if (!task.isSuccessful) {
                msg = "Subscribe failed"
            }

//           Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

}