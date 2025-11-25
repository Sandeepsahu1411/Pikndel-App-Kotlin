package com.example.pikndelappkotlin.presentation.screens.auth_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.key.Key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.presentation.navigation.Routes
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.CustomButton
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.CustomTextField

@Composable
fun OtpScreenUI(navController: NavController, phoneNumber: String?) {

    var otp by remember { mutableStateOf("") }
    Column(

    ) {
    IconButton(onClick = { navController.navigateUp()}) {
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
                text = "Enter 4 digit OTP sent to you at +91 $phoneNumber.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = "Edit mobile number",
                style = MaterialTheme.typography.labelLarge,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable {
                    navController.navigate(Routes.LoginScreenRoute)
                }

            )
            Spacer(Modifier.height(25.dp))


            Text(
                text = "OTP",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 5.dp),
                fontWeight = FontWeight.Medium
            )

            OtpInputField(
                onOtpChange = { updatedOtp ->
                    otp = updatedOtp
                },
                onOtpComplete = { completedOtp ->
                    otp = completedOtp
                }
            )
            Spacer(Modifier.weight(1f))
            CustomButton(
                text = "Verify",
                onClick = {
                    navController.navigate(Routes.HomeScreenRoute)
                }
            )
            Text(
                text = "Resend 1:00",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(10.dp))


        }
    }


}


@Composable
fun OtpInputField(
    otpLength: Int = 4,
    onOtpComplete: (String) -> Unit,
    onOtpChange: (String) -> Unit
) {
    val otpValues = remember { mutableStateListOf(*Array(otpLength) { "" }) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (i in 0 until otpLength) {
            Box(
                modifier = Modifier
//                    .weight(1f)
//                    .aspectRatio(1f)
                    .width(50.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White)
                    .focusRequester(focusRequesters[i])
                    .onPreviewKeyEvent { keyEvent ->
                        if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Backspace) {
                            if (otpValues[i].isNotEmpty()) {
                                otpValues[i] = ""
                            } else if (i > 0) {
                                otpValues[i - 1] = ""
                                focusRequesters[i - 1].requestFocus()
                            }
                            true
                        } else {
                            false
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = otpValues[i],
                    onValueChange = { value ->
                        if (value.length == 1 && value.all { it.isDigit() }) {
                            otpValues[i] = value
                            if (i < otpLength - 1) {
                                focusRequesters[i + 1].requestFocus()
                            }
                        } else if (value.isEmpty()) {
                            otpValues[i] = ""
                        }

                        val otp = otpValues.joinToString("")
                        onOtpChange(otp)
                        if (otp.length == otpLength) {
                            onOtpComplete(otp)
                        }
                    },

                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 20.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .wrapContentHeight(Alignment.CenterVertically),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            innerTextField()
                        }
                    }
                )
            }
        }
    }

    // Auto-focus first box
    LaunchedEffect(Unit) {
        focusRequesters.first().requestFocus()
    }
}