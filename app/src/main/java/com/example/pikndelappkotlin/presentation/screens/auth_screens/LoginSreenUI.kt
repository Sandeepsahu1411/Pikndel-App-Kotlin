package com.example.pikndelappkotlin.presentation.screens.auth_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.presentation.navigation.Routes
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomButton
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomTextField

@Composable
fun LoginScreenUI(navController: NavController) {

    var number by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
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
                    32
                    Icon(
                        imageVector = Icons.Default.PhoneIphone,
                        contentDescription = null,
                    )
                },

                trailingIcon = {
                    if (number.isNotEmpty()) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable {
                                    number = ""
                                }
                        )
                    } else {
                        null
                    }
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
