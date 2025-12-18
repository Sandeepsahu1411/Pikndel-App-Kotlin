package com.example.pikndelappkotlin.presentation.screens.common_composable


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalendar() {

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )
    val events = remember {
        mapOf(
            LocalDate.now() to listOf("Meeting", "Birthday"),
            LocalDate.now().plusDays(2) to listOf("Holiday")
        )
    }
    val festivalEvents = remember {
        listOf(
            LocalDate.of(2025, 1, 2), // Makar Sankranti
            LocalDate.of(2025, 1, 14), // Makar Sankranti
            LocalDate.of(2025, 1, 20), // Makar Sankranti
            LocalDate.of(2025, 8, 15), // Independence Day
            LocalDate.of(2025, 10, 2)  // Gandhi Jayanti
        )
    }

    val scope = rememberCoroutineScope()
    val selectedDate = remember { mutableStateOf(LocalDate.now()) }
    val leaveDates = remember { mutableStateListOf<LocalDate>() }
    Card(

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier.padding( 15.dp)
    )
    {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    // Navigate to the previous month
                    scope.launch {

                        state.scrollToMonth(state.firstVisibleMonth.yearMonth.minusMonths(1))
                    }
                    
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Previous Month",
                        tint = Color.White

                    )
                }


                Text(
                    text = "${state.firstVisibleMonth.yearMonth.month.name} ${state.firstVisibleMonth.yearMonth.year}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    // Navigate to the next month
                    scope.launch {
                        state.scrollToMonth(state.firstVisibleMonth.yearMonth.plusMonths(1))
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Next Month",
                        tint = Color.White

                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            DaysOfWeekTitle(daysOfWeek = daysOfWeek())

            HorizontalCalendar(

                userScrollEnabled = false,
                state = state,
                dayContent = {
                    Day(it, selectedDate.value, leaveDates, events, festivalEvents) { day ->
                        if (leaveDates.contains(day.date)) {
                            leaveDates.remove(day.date)
                        } else {
                            leaveDates.add(day.date)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
    }


    LegendRow(
        items = listOf(
            Legend("Present", Color(0xFF2E7D32)),
            Legend("Half Day", Color(0xFF81C784)),
            Legend("Leave", Color(0xFFFBC02D)),
            Legend("Absent", Color(0xFFD32F2F)),
            Legend("Holiday", Color(0xFF9E9E9E)),
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(
    day: CalendarDay,
    selectedDate: LocalDate,
    leaveDates: SnapshotStateList<LocalDate>,
    events: Map<LocalDate, List<String>>,
    festivalEvents: List<LocalDate>,
    onClick: (CalendarDay) -> Unit,

    ) {
    val isToday = day.date == LocalDate.now()
    val isSelected = day.date == selectedDate
    val isSunday = day.date.dayOfWeek == DayOfWeek.SUNDAY
    val isFestival = festivalEvents.contains(day.date)

    // Fill the entire calendar cell so weeks/days align perfectly under headers.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // square cell
            .padding(2.dp)
            .clip(CircleShape)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // Draw the circular selection/today background centered inside the cell
            val circleColor =
                when {
                    isToday -> MaterialTheme.colorScheme.primary
                    isSelected -> MaterialTheme.colorScheme.secondary
                    else -> Color.Transparent
                }
            if (circleColor != Color.Transparent) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
//                        .background(circleColor)
                )
            }
            // Display Day Number
            Text(
                text = day.date.dayOfMonth.toString(),
                fontSize = 14.sp,
                color =
                    when {
                        isSunday || isFestival -> Color.Red
//                        isToday -> Color.White
                        day.position == DayPosition.MonthDate -> Color.Black
                        else -> Color.Gray
                    }
            )

            // Display Event Dots Below the Day Number
            if (events[day.date] != null || isFestival) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter) // Align dots below the day
                        .padding(top = 4.dp), // Slight padding from the bottom
                    horizontalArrangement = Arrangement.Center
                ) {
                    events[day.date]?.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .padding(1.dp)
                                .background(
                                    color = when {
                                        isToday -> Color.Blue
                                        else -> MaterialTheme.colorScheme.primary
                                    },
                                    shape = CircleShape
                                )
                        )
                    }
                    if (isFestival) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .padding(1.dp)
                                .background(
                                    color = Color.Red,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {

            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
            )
        }
    }
}

// --- Legend (Present, Half Day, Leave, Absent, Holiday) ---
data class Legend(val label: String, val color: Color)

@Composable
private fun LegendRow(items: List<Legend>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            LegendItem(color = item.color, label = item.label)
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall)
    }
}


