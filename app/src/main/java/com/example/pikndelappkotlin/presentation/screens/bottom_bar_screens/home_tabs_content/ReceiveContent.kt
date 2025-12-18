package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomButton
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomLoader

@Composable
fun ReceiveContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CustomLoader(
                targetSizeDp = 30
            )
        }
        CustomButton(
            onClick = {},
            text = "Scan",
            isIcon = true,
            icon = Icons.Default.QrCodeScanner,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(20.dp))
    }

}
