package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.AttendanceContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.BoxScanContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.BranchContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.DeliveryContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.PickupContent
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.ReceiveContent
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.CustomTopBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreenUI(navController: NavController) {
    Column {
        CustomTopBar(
            navController = navController,
        )

        val tabs = remember {
            listOf(
                FilterItem("Branch", Icons.Default.Apartment),
                FilterItem("Delivery 0/0", Icons.Default.AllInbox),
                FilterItem("Pickup", Icons.Default.LocalShipping),
                FilterItem("Attendance", Icons.Default.CalendarMonth),
                FilterItem("Receive", Icons.Default.FitScreen),
                FilterItem("BoxScan", Icons.Default.QrCodeScanner),
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
            stateHolder.SaveableStateProvider(key = "home_pager_$page") {
                when (page) {
                    0 -> BranchContent()
                    1 -> DeliveryContent()
                    2 -> PickupContent()
                    3 -> AttendanceContent()
                    4 -> ReceiveContent()
                    5 -> BoxScanContent()
                }
            }
        }
    }
}

@Composable
fun TaskBoardTopRow(
    tabs: List<FilterItem>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTab,
        edgePadding = 12.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        divider = { HorizontalDivider(thickness = 1.dp, color = Color.LightGray) },
        indicator = { positions ->
            TabRowDefaults.Indicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .tabIndicatorOffset(positions[selectedTab])
                    .padding(horizontal = 1.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )
        }
    ) {
        tabs.forEachIndexed { index, item ->
            val selected = index == selectedTab
            Tab(
                selected = selected,
                onClick = { onTabSelected(index) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ){
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

data class FilterItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit = {}
)



