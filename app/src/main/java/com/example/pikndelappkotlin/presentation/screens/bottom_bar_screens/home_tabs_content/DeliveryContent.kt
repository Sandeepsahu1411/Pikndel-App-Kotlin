package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomButton
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomCircleIconButton

data class DeliveryItem(
    val docketNo: String,
    val customerName: String,
    val address: String,
    val phone: String? = null,
    val amount: Double? = null,
    val status: String = "Pending",
    val isCod: Boolean = false,
    val partner: String = "DS SOLUCTION",
    val paymentTag: String = "Pre-Paid",
    val categoryTag: String = "ECOMMERCE"
)

@Composable
fun DeliveryContent(
    items: List<DeliveryItem> = demoDeliveryItems(),
    onItemClick: (DeliveryItem) -> Unit = {},
    onScanSearchClick: () -> Unit = {},
    onReachClick: () -> Unit = {}
) {
    DeliveryListScreen(
        items = items,
        onItemClick = onItemClick,
        onScanSearchClick = onScanSearchClick,
        onReachClick = onReachClick
    )
}

@Composable
fun DeliveryListScreen(
    items: List<DeliveryItem>,
    onItemClick: (DeliveryItem) -> Unit,
    onScanSearchClick: () -> Unit,
    onReachClick: () -> Unit
) {
//    val overlayHeight = 50.dp
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 1.dp,
                bottom = 70.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
//                EarningsBanner(
//                    todayAmount = 100.0,
//                    potentialAmount = 2000.0
//                )
            }
            items(items) { item ->
                DeliveryCardV2(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }

        DeliveryOverlayActions(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp),
            onScanSearchClick = onScanSearchClick,
            onReachClick = onReachClick
        )
    }
}

@Composable
private fun EarningsBanner(
    todayAmount: Double,
    potentialAmount: Double

) {
    Surface(
        color = Color(0xFFE9F5EC),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Today's earnings",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF49614F)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "₹${todayAmount.toInt()}",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "Potential earnings are upto ₹${potentialAmount.toInt()}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF7A8D80)
            )
        }
    }
}


@Composable
private fun DeliveryCardV2(
    item: DeliveryItem,
    onClick: () -> Unit
) {
    var expanded by rememberSaveable(item.docketNo) { mutableStateOf(true) }
    val cardShape = RoundedCornerShape(12.dp)

    ElevatedCard(
        onClick = {
            if (!expanded) {
                expanded = true
            } else {
                onClick()
            }
        },
        shape = cardShape,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, cardShape, clip = false)
            .clip(cardShape)
            .animateContentSize(),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: name + verified + status + actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = item.customerName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.Verified,
                        contentDescription = "verified",
                        tint = Color(0xFF00C853),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    StatusChip(
                        text = item.status,
                        container = Color(0xFFFFEBEE),
                        content = Color(0xFFD32F2F),
                        border = Color(0xFFFFCDD2)
                    )
                    CustomCircleIconButton(
                        icon = Icons.Filled.LocationOn,
                        contentDesc = "Navigate",
                        onClick = { /* map */ },
                    )
                    CustomCircleIconButton(
                        icon = Icons.Filled.Call,
                        contentDesc = "Call",
                        onClick = { /* call */ }
                    )
                }
            }

            Spacer(Modifier.height(6.dp))

            Text(
                text = item.docketNo,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = item.address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            if (expanded) {
                Spacer(Modifier.height(8.dp))

                Text(
                    text = item.partner,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TagChip(
                        text = item.paymentTag,
                        container = Color(0xFFE7F6EA),
                        content = Color(0xFF2E7D32),
                        border = Color(0xFFB9E5C2)
                    )
                    TagChip(
                        text = item.categoryTag,
                        container = Color(0xFFF2F2F2),
                        content = Color(0xFF616161),
                        border = Color(0xFFE0E0E0)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Show less",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { expanded = false }
                )
            }
        }
    }
}

@Composable
private fun StatusChip(
    text: String,
    container: Color,
    content: Color,
    border: Color
) {
    Surface(
        color = container,
        contentColor = content,
        shape = RoundedCornerShape(8.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
            brush = SolidColor(border)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp),
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1
        )
    }
}

@Composable
private fun TagChip(
    text: String,
    container: Color,
    content: Color,
    border: Color
) {
    Surface(
        color = container,
        contentColor = content,
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
            brush = SolidColor(border)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}


@Composable
private fun DeliveryOverlayActions(
    modifier: Modifier = Modifier,
    onScanSearchClick: () -> Unit,
    onReachClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CustomButton(
            text = "Scan search",
            onClick = { onScanSearchClick },
            isIcon = true,
            icon = Icons.Filled.QrCodeScanner,
            modifier = Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(Modifier.width(8.dp))
        CustomButton(
            text = "Reach Office",
            onClick = { onReachClick },
            isIcon = true,
            icon = Icons.Filled.LocationOn,
            modifier = Modifier.wrapContentWidth(),
//            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp)

        )
    }
}

fun demoDeliveryItems(): List<DeliveryItem> = listOf(
    DeliveryItem(
        docketNo = "DIN000327",
        customerName = "Rohit Rajput",
        address = "B-84 Okhla, Phase 2, New Delhi\n110020 Delhi/NCR-110020",
        phone = "9876543210",
        amount = 0.0,
        status = "Out of TAT",
        isCod = false
    ),
    DeliveryItem(
        docketNo = "DIN000377",
        customerName = "Rohit Rajput",
        address = "B-84 Okhla, Phase 2, New Delhi\n110020 Delhi/NCR-110020",
        phone = "9811122233",
        amount = 0.0,
        status = "Out of TAT",
        isCod = false
    ),
    DeliveryItem(
        docketNo = "DIN000378",
        customerName = "Rohit Rajput",
        address = "B-84 Okhla, Phase 2, New Delhi\n110020 Delhi/NCR-110020",
        phone = "9811122233",
        amount = 0.0,
        status = "Out of TAT",
        isCod = false
    ),
    DeliveryItem(
        docketNo = "DIN000379",
        customerName = "Rohit Rajput",
        address = "B-84 Okhla, Phase 2, New Delhi\n110020 Delhi/NCR-110020",
        phone = "9811122233",
        amount = 0.0,
        status = "Out of TAT",
        isCod = false
    )
)


