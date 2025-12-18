package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.CustomDropdownMenu
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SummaryRegularizationContent(modifier: Modifier = Modifier) {
    val monthFormatter = remember { DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()) }
    val now = remember { YearMonth.now() }
    val months = remember { listOf(now, now.minusMonths(1), now.minusMonths(2)) }
    var selectedMonth by remember { mutableStateOf(months.first()) }

    // Collapsing header (dropdown) same as Table tab
    val listState: LazyListState = rememberLazyListState()
    var headerHeightPx by remember { mutableIntStateOf(0) }
    var headerOffsetPx by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = (headerOffsetPx + delta).coerceIn(-headerHeightPx.toFloat(), 0f)
                val consumed = newOffset - headerOffsetPx
                headerOffsetPx = newOffset
                return Offset(0f, consumed)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (consumed.y != 0f) {
                    val newOffset =
                        (headerOffsetPx + consumed.y).coerceIn(-headerHeightPx.toFloat(), 0f)
                    headerOffsetPx = newOffset
                }
                return Offset.Zero
            }
        }
    }
    val visibleHeaderPx = (headerHeightPx + headerOffsetPx).coerceIn(0f, headerHeightPx.toFloat())
    val topPadding = with(density) { visibleHeaderPx.toDp() } + 0.dp
    // Dummy rows
    val rows = remember {
        listOf(
            SummaryRow("23/11/25", "10:30 AM\n07:20 PM", "0.5", "Applied"),
            SummaryRow("11/11/25", "10:55 AM\n07:14 PM", "0.5", "Applied"),
            SummaryRow("08/11/25", "10:19 AM\n07:00 PM", "0.5", "Applied"),
            SummaryRow("23/11/25", "10:30 AM\n07:20 PM", "0.5", "Applied"),
            SummaryRow("11/11/25", "10:55 AM\n07:14 PM", "0.5", "Applied"),
            SummaryRow("08/11/25", "10:19 AM\n07:00 PM", "0.5", "Applied"),
            SummaryRow("08/11/25", "10:19 AM\n07:00 PM", "0.5", "Applied"),
            SummaryRow("23/11/25", "10:30 AM\n07:20 PM", "0.5", "Applied"),
            SummaryRow("11/11/25", "10:55 AM\n07:14 PM", "0.5", "Applied"),
            SummaryRow("08/11/25", "10:19 AM\n07:00 PM", "0.5", "Applied"),
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .clipToBounds()
    ) {
        // Floating month dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, headerOffsetPx.toInt()) }
                .onGloballyPositioned { headerHeightPx = it.size.height }
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            CustomDropdownMenu(
                label = monthFormatter.format(selectedMonth),
                options = months,
                selectedOption = selectedMonth,
                onOptionSelected = { selectedMonth = it },
                optionLabel = { it.format(monthFormatter) },
                dropdownHeight = 150.dp
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            Spacer(Modifier.height(topPadding))

            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                item {
                    StatsGrid()
                }
                // Keep title and header row pinned together as one sticky block
                stickyHeader {
                    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)) {
                        Text(
                            text = "Data For Regularization",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            tonalElevation = 2.dp,
                            shadowElevation = 8.dp,
                            modifier = Modifier.padding(top = 5.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Date",
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.weight(0.25f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "Day Start/End",
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.weight(0.35f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "D.D",
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.weight(0.15f),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "Action",
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.weight(0.2f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } 
                }
                items(rows) { r ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            r.date, modifier = Modifier.weight(0.25f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            r.daySpan,
                            modifier = Modifier.weight(0.35f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                        )
                        Text(
                            r.dd,
                            modifier = Modifier.weight(0.15f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge
                        )
                        ActionPill(r.action, modifier = Modifier.weight(0.2f))
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            }
        }
    }
}

private data class SummaryRow(
    val date: String,
    val daySpan: String,
    val dd: String,
    val action: String
)

@Composable
private fun ActionPill(text: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 0.dp,
        modifier = modifier
            .wrapContentWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun StatsGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCard(title = "Present", value = "25", modifier = Modifier.weight(1f))
            StatCard(title = "Leave", value = "1", modifier = Modifier.weight(1f))
            StatCard(title = "LWP", value = "0", modifier = Modifier.weight(1f))
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            StatCard(title = "Deduction", value = "1.5", modifier = Modifier.weight(1f))
            StatCard(title = "Payment Days", value = "24.5", modifier = Modifier.weight(1f))
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = title,
                lineHeight = 12.sp,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                lineHeight = 14.sp,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

