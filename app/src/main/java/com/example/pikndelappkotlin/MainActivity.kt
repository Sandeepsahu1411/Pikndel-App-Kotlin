package com.example.pikndelappkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pikndelappkotlin.presentation.navigation.AppNavigation
import com.example.pikndelappkotlin.presentation.navigation.SetStatusBarColor
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.rememberImeState
import com.example.pikndelappkotlin.ui.theme.BsaPrimary
import com.example.pikndelappkotlin.ui.theme.PikndelAppKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        AppNavigation()
                    }

                }

            }
        }
    }
}

