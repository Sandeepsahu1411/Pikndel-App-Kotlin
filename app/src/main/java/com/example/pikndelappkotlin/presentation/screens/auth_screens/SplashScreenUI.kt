package com.example.pikndelappkotlin.presentation.screens.auth_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.presentation.navigation.Routes
import com.example.pikndelappkotlin.presentation.navigation.SubRoutes
import com.example.pikndelappkotlin.R
import com.example.pikndelappkotlin.ui.theme.BsaPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreenUI(navController: NavController) {
//    SetStatusBarColor(
//        statusBarColor = BsaPrimary,
//        navigationBarColor = BsaPrimary,
//        darkIcons = false
//    )

//    var token by rememberSaveable {
//        mutableStateOf("")
//    }
//    LaunchedEffect(key1 = Unit) {
//        userPreferenceManager.userID.collect {
//            if (it != null) {
//                token = it
//            }
//        }
//    }
    LaunchedEffect(key1 = true) {
        delay(3000)
//        if (token.isNotBlank()) {
//            navController.navigate(SubRoutes.MainScreenRoute ) {
//
//                popUpTo(Routes.SplashScreenRoute) {
//                    inclusive = true
//                }
//            }
//        } else {
        navController.navigate(Routes.LoginScreenRoute) {
            popUpTo(Routes.SplashScreenRoute) {
                inclusive = true
            }
        }
//        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.pikndelsplashlogo),
                contentDescription = "Splash",
                modifier = Modifier.size(200.dp),
            )
        }
        Text(
            text = "Version 50.8",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

    }

}

