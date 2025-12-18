package com.example.pikndelappkotlin.presentation.screens.common_composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.R
import com.example.pikndelappkotlin.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String = "",
    navController: NavController,

    ) {
    val coroutineScope = rememberCoroutineScope()
    var profileDialog by remember { mutableStateOf<Boolean>(false) }
    val context = LocalContext.current


    Row(
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (!title.isNotEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.pikndelsplashlogo),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp, 50.dp)
                    .padding(start = 10.dp)
            )
        }else{
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomCircleIconButton(icon = Icons.Outlined.Search) {
                // Search click
            }

            CustomCircleIconButton(icon = Icons.Outlined.Menu) {
                // Menu click
            }

            CustomCircleIconButton(icon = Icons.Outlined.Notifications) {
                navController.navigate(Routes.NotificationScreenRoute)
            }
        }

    }

}

@Composable
fun CustomCircleIconButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconSize: Dp = 25.dp,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(35.dp)
            .clip(CircleShape)
            .border(1.dp, Color.LightGray, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )
    }
}
