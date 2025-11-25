package com.example.pikndelappkotlin.presentation.screens.utils.commonUtils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.pikndelappkotlin.ui.theme.BsaBackground

@Composable
fun CustomBottomBar(selectedItemIndex: Int, onItemSelected: (Int) -> Unit) {

    val items = listOf(
        BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavigationItem("More", Icons.Filled.Category, Icons.Outlined.Category),
        BottomNavigationItem(
            "Report", Icons.AutoMirrored.Filled.InsertDriveFile,
            Icons.AutoMirrored.Outlined.InsertDriveFile
        ),
        BottomNavigationItem("Profile", Icons.Filled.Person, Icons.Outlined.Person)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .height(60.dp)
            .background(color = BsaBackground),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->

            val icon =
                if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onItemSelected(index)
                    }, contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = if (selectedItemIndex == index) Modifier.offset(y = (-10).dp) else Modifier
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (selectedItemIndex == index) MaterialTheme.colorScheme.primary else Color.Transparent,
                                RoundedCornerShape(15.dp, 15.dp, 5.dp, 5.dp)
                            )
                            .size(45.dp, 40.dp), contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = icon,
                            contentDescription = item.title,
                            tint = if (selectedItemIndex == index) MaterialTheme.colorScheme.onPrimary else Color.DarkGray,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(CircleShape)
                                .clickable { onItemSelected(index) })
                    }

                    AnimatedVisibility(visible = selectedItemIndex == index) {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

            }
        }
    }

}

data class BottomNavigationItem(
    val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector

)