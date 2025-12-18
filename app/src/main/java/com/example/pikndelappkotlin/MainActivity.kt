package com.example.pikndelappkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pikndelappkotlin.presentation.navigation.AppNavigation
import com.example.pikndelappkotlin.presentation.navigation.SetStatusBarColor
import com.example.pikndelappkotlin.utils.permissions.RequestAppPermissionsOnce
import com.example.pikndelappkotlin.ui.theme.BsaPrimary
import com.example.pikndelappkotlin.ui.theme.PikndelAppKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            PikndelAppKotlinTheme {
                SetStatusBarColor(
                    statusBarColor = BsaPrimary,
                    navigationBarColor = BsaPrimary,
                    darkIcons = false
                )
                Scaffold(
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
//                            .padding(innerPadding)
                    ) {
                        // Request all app permissions once on start
                        RequestAppPermissionsOnce()
                        AppNavigation()
                    }

                }

            }
        }
    }
}

