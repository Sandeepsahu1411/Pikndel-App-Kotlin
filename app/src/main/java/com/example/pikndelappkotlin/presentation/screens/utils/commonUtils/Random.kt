package com.example.pikndelappkotlin.presentation.screens.utils.commonUtils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HomeTopTabs(
    modifier: Modifier = Modifier,
    tabs: List<String> = listOf("Branch", "Delivery", "Pickup", "Receive", "Box", "Scan"),
    indicatorColor: Color = Color(0xFF1976D2), // Blue
    onTabChanged: (Int) -> Unit = {}
) {
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(3.dp),
                    color = indicatorColor
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                val selected = pagerState.currentPage == index
                Tab(
                    selected = selected,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                        onTabChanged(index)
                    },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (selected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = true
        ) { page ->
            when (page) {
                0 -> BranchPage()
                1 -> DeliveryPage()
                2 -> PickupPage()
                3 -> ReceivePage()
                4 -> BoxPage()
                5 -> ScanPage()
                else -> PlaceholderPage(tabs[page])
            }
        }
    }
}

@Composable
private fun PlaceholderPage(title: String) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(text = title)
    }
}

@Composable private fun BranchPage() = PlaceholderPage("Branch")
@Composable private fun DeliveryPage() = PlaceholderPage("Delivery")
@Composable private fun PickupPage() = PlaceholderPage("Pickup")
@Composable private fun ReceivePage() = PlaceholderPage("Receive")
@Composable private fun BoxPage() = PlaceholderPage("Box")
@Composable private fun ScanPage() = PlaceholderPage("Scan")

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun HomeTopTabsPreview() {
    MaterialTheme {
        HomeTopTabs()
    }
}