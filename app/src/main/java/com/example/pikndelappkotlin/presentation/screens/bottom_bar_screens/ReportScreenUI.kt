package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.CustomTopBar

@Composable
fun ReportScreenUI(navController: NavController) {
//    Column {
//        CustomTopBar(
//            title = "Report",
//            navController = navController
//        )
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(text = "ItsReportScreen")
//        }
//    }
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Report",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )
    }
}