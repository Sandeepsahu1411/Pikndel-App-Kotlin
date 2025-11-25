package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FireTruck
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.MoreGrid
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.MoreItem

@Composable
 fun BranchContent(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        val items = remember {
            listOf(
                MoreItem("In-scan Bag", Icons.Outlined.FireTruck) {},
                MoreItem("In-scan Shipment", Icons.Filled.Inventory2) {},
                MoreItem("Shipment Allocate ", Icons.Outlined.Person) {},
                MoreItem("Receive from FE", Icons.Outlined.AllInbox) {},
                MoreItem("Branch Audit", Icons.Outlined.CheckCircle) {},
            )
        }
        MoreGrid(items = items)
    }
}
