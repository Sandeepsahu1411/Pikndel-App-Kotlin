package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Toll
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class MoreItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun MoreScreenUI(navController: NavController) {
    val context = LocalContext.current

    val items = remember { listOf(
        MoreItem("RTO/POD Delivery", Icons.Filled.Inventory2) {
            navController.navigate("rto_pod")
        },
        MoreItem("Cartage Delivery", Icons.Filled.LocalShipping) {
            navController.navigate("cartage_delivery")
        },
        MoreItem("Attendance", Icons.Filled.CalendarMonth) {
            navController.navigate("attendance")
        },
        MoreItem("Other Tasks", Icons.AutoMirrored.Filled.ListAlt) {
            navController.navigate("other_tasks")
        },

        // Non-navigation actions:
        MoreItem("Download Cases", Icons.Filled.Download) {
        },
        MoreItem("Upload Cases", Icons.Filled.Upload) {
        },
        MoreItem("Pick up Receiving", Icons.Filled.DeliveryDining) {
            navController.navigate("pickup_receiving")
        },
        MoreItem("Bagging", Icons.Filled.ShoppingBag) {
            navController.navigate("bagging")
        },
        MoreItem("Docket", Icons.Filled.AddShoppingCart) {
            navController.navigate("docket")
        },
        MoreItem("IOCM", Icons.Filled.BubbleChart) {
            navController.navigate("iocm")
        },
        MoreItem("File Share", Icons.Filled.Folder) {
            shareText(context, subject = "Share", text = "Hello from BSA")
        },
        MoreItem("Clear Database", Icons.Filled.Storage) {
        },
        MoreItem("Service Provider", Icons.Filled.Toll) {
        },
        MoreItem("Submit To Process", Icons.Filled.WifiTethering) {
        },

        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "More Options",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )

        MoreGrid(items = items)
    }
}

@Composable
fun MoreGrid(
    items: List<MoreItem>,
    minTileSize: Dp = 120.dp,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    shape: Shape = RoundedCornerShape(12.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = minTileSize),
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items, key = { it.title }) { item ->
            MoreGridCard(
                title = item.title,
                icon = item.icon,
                onClick = item.onClick,
                shape = shape
            )
        }
    }
}

@Composable
fun MoreGridCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(12.dp)
) {
    ElevatedCard(
        onClick = onClick,
        shape = shape,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(35.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp,
                maxLines = 2
            )
        }
    }
}


fun shareText(context: Context, subject: String, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, subject))
}

