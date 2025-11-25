package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.CustomDropdownMenu
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

private data class AttendanceRow(val date: LocalDate, val dayStart: String, val dayEnd: String)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TableContent(modifier: Modifier = Modifier) {
    val monthFormatter = remember { DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()) }
    val now = remember { YearMonth.now() }
    val last3Months = remember {
        listOf(now, now.minusMonths(1), now.minusMonths(2))
    }
    var selectedMonth by remember { mutableStateOf<YearMonth?>(last3Months.first()) }

    // Dummy rows (would come from API later)
    val allRows = remember {
        buildList {
            val start = now.minusMonths(2).atDay(1)
            repeat(90) { i ->
                val date = start.plusDays(i.toLong())
                val mod = date.dayOfWeek.value % 5
                val (startStr, endStr) = when (mod) {
                    0 -> "10:%02d AM".format((i % 50) + 10) to "Pending"
                    1 -> "Absent" to "Absent"
                    2 -> "10:%02d AM".format((i % 50) + 10) to "Missing"
                    3 -> "04:%02d PM".format((i % 50) + 10) to "Missing"
                    else -> "--" to "--"
                }
                add(AttendanceRow(date, startStr, endStr))
            }
        }
    }

    val filtered = remember(selectedMonth) {
        allRows.filter { YearMonth.from(it.date) == selectedMonth }
    }

    // Collapsing header that moves with scroll; does not overlap tabs due to clipToBounds.
    val listState: LazyListState = rememberLazyListState()
    var headerHeightPx by remember { mutableIntStateOf(0) }
    var headerOffsetPx by remember { mutableFloatStateOf(0f) } // 0 (shown) .. -headerHeight (hidden)
    val density = LocalDensity.current

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = (headerOffsetPx + delta).coerceIn(-headerHeightPx.toFloat(), 0f)
                val consumedY = newOffset - headerOffsetPx
                headerOffsetPx = newOffset
                return Offset(0f, consumedY)
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                if (consumed.y != 0f) {
                    val newOffset = (headerOffsetPx + consumed.y).coerceIn(-headerHeightPx.toFloat(), 0f)
                    headerOffsetPx = newOffset
                }
                return Offset.Zero
            }
        }
    }
    // Dynamic spacer equals currently visible header height, so content moves up when header hides
    val visibleHeaderPx = (headerHeightPx + headerOffsetPx).coerceIn(0f, headerHeightPx.toFloat())
    val topPadding = with(density) { visibleHeaderPx.toDp() } + 12.dp

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
            .clipToBounds()
    ) {
        // Floating month dropdown (collapsible)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, headerOffsetPx.toInt()) }
                .onGloballyPositioned { headerHeightPx = it.size.height }
                .padding(horizontal = 12.dp)
                .padding(top= 12.dp)
        ) {
            CustomDropdownMenu(
                dropdownHeight = 150.dp,
                label = "Select month",
                options = last3Months,
                selectedOption = selectedMonth,
                onOptionSelected = { selectedMonth = it },
                optionLabel = { it.format(monthFormatter) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 0.dp)
        ) {
            // Keep content below the (possibly hidden) header
            Spacer(Modifier.height(topPadding))

        // Header row (use consistent weights so columns align with rows)
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 2.dp,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    "Date",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Day Start",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Day End",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(filtered) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        row.date.format(
                            DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            )
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    val startColor = if (row.dayStart.equals("Absent", true)) Color(0xFFD32F2F) else MaterialTheme.colorScheme.onSurface
                    val endColor = if (row.dayEnd.equals("Absent", true)) Color(0xFFD32F2F) else MaterialTheme.colorScheme.onSurface

                    Text(
                        row.dayStart,
                        color = startColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        row.dayEnd,
                        color = endColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Divider-ish spacer
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFE0E0E0))
                )
            }
        }
        }
    }
}