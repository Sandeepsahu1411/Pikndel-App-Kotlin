package com.example.pikndelappkotlin.presentation.screens.auth_screens

import android.R.attr.text
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.pikndelappkotlin.R
import com.example.pikndelappkotlin.presentation.navigation.Routes
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.CustomButton
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.CustomTextField
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.rememberImeState

@Composable
fun LoginScreenUI(navController: NavController) {

    var number by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var otp by remember { mutableStateOf("") }
    Column(

    ) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .imePadding()

        ) {

            Text(
                text = "Enter Your registered mobile number to login.",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = "We will send you an OTP to confirm it's you",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(25.dp))


            Text(
                text = "Mobile Number",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 5.dp),
                fontWeight = FontWeight.Medium
            )
            CustomTextField(
                value = number,
                onValueChange = { newValue ->
                    val filtered = newValue.filter { char -> !char.isWhitespace() }
                    if (filtered.length <= 10) {
                        number = filtered
                    }
                    // Clear error as user edits
                    errorMessage = null
                },
                placeholderText = "+91 XXXXXXXXXX",
                onlyNumbers = true,
                keyboardType = KeyboardType.Number,
                isError = !errorMessage.isNullOrBlank(),
                errorMessage = errorMessage,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.PhoneIphone,
                        contentDescription = null,
                    )
                },
              onImeAction = {
                  if (number.length == 10) {
                      navController.navigate(Routes.OtpScreenRoute(number))
                  } else {
                      errorMessage = "Enter a valid 10-digit mobile number"
                  }
              }
            )
            Spacer(Modifier.weight(1f))
            CustomButton(
                text = "Next",
                onClick = {
                    if (number.length == 10) {
                        navController.navigate(Routes.OtpScreenRoute(number))
                    } else {
                        errorMessage = "Enter a valid 10-digit mobile number"
                    }
                }
            )
            Spacer(Modifier.height(15.dp))


        }


    }
}
