package com.example.pikndelappkotlin.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.pikndelappkotlin.presentation.screens.auth_screens.LoginScreenUI
import com.example.pikndelappkotlin.presentation.screens.auth_screens.OtpScreenUI
import com.example.pikndelappkotlin.presentation.screens.auth_screens.SplashScreenUI
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.HomeScreenUI
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.MoreScreenUI
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.ProfileScreenUI
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.ReportScreenUI
import com.example.pikndelappkotlin.presentation.screens.other_screens.NotificationScreenUI
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomBottomBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val screensWithBottomBar = listOf(
        Routes.HomeScreenRoute::class.qualifiedName,
        Routes.MoreScreenRoute::class.qualifiedName,
        Routes.ReportScreenRoute::class.qualifiedName,
        Routes.ProfileScreenRoute::class.qualifiedName
    )
    val currentDestination by navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination?.destination?.route

    val shouldShowBottomBar = remember(currentRoute) {
        mutableStateOf(currentRoute in screensWithBottomBar)
    }
    currentDestination?.destination?.route in screensWithBottomBar

    var selectedItemIndex by remember(currentRoute) {
        mutableIntStateOf(screensWithBottomBar.indexOf(currentRoute).takeIf { it >= 0 } ?: 0)
    }


    Scaffold(
        bottomBar = {

            if (shouldShowBottomBar.value) {
                Box(
                    modifier = Modifier
                ) {
                    CustomBottomBar(
                        selectedItemIndex = selectedItemIndex,
                        onItemSelected = { index ->
                            selectedItemIndex = index
                            val route = when (index) {
                                0 -> Routes.HomeScreenRoute
                                1 -> Routes.MoreScreenRoute
                                2 -> Routes.ReportScreenRoute
                                3 -> Routes.ProfileScreenRoute
                                else -> Routes.HomeScreenRoute
                            }
                            navController.navigate(route) {
                                popUpTo(SubRoutes.MainSubRoute) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                }
            }
        },
        modifier = Modifier
            .imePadding()
//            .systemBarsPadding()
    )
    { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {
            NavHost(navController = navController, startDestination = SubRoutes.MainSubRoute) {

                navigation<SubRoutes.AuthSubRoute>(startDestination = Routes.SplashScreenRoute) {
                    composable<Routes.SplashScreenRoute> {
                        SplashScreenUI(navController = navController)
                    }

                    composable<Routes.OtpScreenRoute> {
                        val phoneNumber = it.arguments?.getString("phoneNumber")
                        OtpScreenUI(navController = navController, phoneNumber = phoneNumber)
                    }
                    composable<Routes.LoginScreenRoute> {
                        LoginScreenUI(navController = navController)
                    }

                }

                navigation<SubRoutes.MainSubRoute>(startDestination = Routes.HomeScreenRoute) {

                    composable<Routes.HomeScreenRoute> {
                        HomeScreenUI(navController = navController)
                    }
                    composable<Routes.MoreScreenRoute> {
                        MoreScreenUI(navController = navController)
                    }
                    composable<Routes.ReportScreenRoute> {
                        ReportScreenUI(navController = navController)
                    }
                    composable<Routes.ProfileScreenRoute> {
                        ProfileScreenUI(navController = navController)
                    }
                    composable<Routes.NotificationScreenRoute> {
                        NotificationScreenUI(navController = navController)
                    }
                }

            }


        }
    }
}

@Composable
fun SetStatusBarColor(
    statusBarColor: Color,
    navigationBarColor: Color,
    darkIcons: Boolean = true
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,

            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = darkIcons
        )
    }
}
