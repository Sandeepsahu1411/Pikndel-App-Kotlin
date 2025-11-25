package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CalendarViewMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content.AvailabilityContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content.CalenderContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content.PunchInOutContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content.TableContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.FilterItem
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.TaskBoardTopRow
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceContent() {
    Column {

        val tabs = remember {
            listOf(
                FilterItem("Punch In/Out", Icons.Outlined.Fingerprint),
                FilterItem("Calender", Icons.Outlined.CalendarMonth),
                FilterItem("Table", Icons.Outlined.CalendarViewMonth),
                FilterItem("Availability", Icons.Outlined.CheckCircle),
            )
        }
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
        var selectedTab by remember { mutableIntStateOf(0) }
        val coroutineScope = rememberCoroutineScope()
        val stateHolder = rememberSaveableStateHolder()

        LaunchedEffect(pagerState.currentPage) {
            selectedTab = pagerState.currentPage
        }

        TaskBoardTopRow(
            tabs = tabs,
            selectedTab = selectedTab,
            onTabSelected = { index ->
                if (index != selectedTab) {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false,
            beyondViewportPageCount = 1
        ) { page ->
            stateHolder.SaveableStateProvider(key = "attendance_pager_$page") {
                when (page) {
                    0 -> PunchInOutContent()
                    1 -> CalenderContent()
                    2 -> TableContent()
                    3 -> AvailabilityContent()
                }
            }
        }
    }
}