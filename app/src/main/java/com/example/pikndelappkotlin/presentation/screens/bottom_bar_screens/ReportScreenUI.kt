package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CalendarViewMonth
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.report_tabs_content.ReportCODContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.report_tabs_content.ReportPacketsContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.report_tabs_content.ReportSummaryContent
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun nrmlprev(modifier: Modifier = Modifier) {
    ReportScreenUI(navController = NavController(context = LocalContext.current))
}
@Composable
fun ReportScreenUI(navController: NavController) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Report",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        )

        Column {

            val tabs = remember {
                listOf(
                    FilterItem("Summary", Icons.Outlined.Fingerprint),
                    FilterItem("Packets", Icons.Outlined.CalendarMonth),
                    FilterItem("COD", Icons.Outlined.CalendarViewMonth),

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
                stateHolder.SaveableStateProvider(key = "attendance1_pager_$page") {
                    when (page) {
                        0 -> ReportSummaryContent()
                        1 -> ReportPacketsContent()
                        2 -> ReportCODContent()

                    }
                }
            }
        }
    }
}