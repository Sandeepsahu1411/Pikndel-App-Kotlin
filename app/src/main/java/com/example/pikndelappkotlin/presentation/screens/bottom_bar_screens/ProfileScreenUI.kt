package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocalPhone
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.CustomDropdownMenu
import com.example.pikndelappkotlin.presentation.screens.utils.commonUtils.CustomButton
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreenUI(navController: NavController) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.verticalScroll(rememberScrollState())
			.padding(horizontal = 16.dp, vertical = 12.dp)
	) {
		// Title
		Text(
			text = "Profile",
			style = MaterialTheme.typography.headlineMedium,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.padding(bottom = 16.dp)
		)

		// Header: avatar + name + phone + report to
		Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
			Box(
				modifier = Modifier
					.size(56.dp)
					.clip(CircleShape)
					.background(Color(0xFFEFF2F6)),
				contentAlignment = Alignment.Center
			) {
				Icon(
					imageVector = Icons.Outlined.Person,
					contentDescription = "Avatar",
					tint = Color(0xFF5A6B7B),
					modifier = Modifier.size(28.dp)
				)
			}
			Spacer(Modifier.width(12.dp))
			Column(modifier = Modifier.weight(1f)) {
				Text(text = "DINESH SOLANKI", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
				Text(text = "7011580991", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
				Text(text = "REPORT TO", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
			}
		}

		Spacer(Modifier.height(16.dp))

		// Earnings card with month dropdown and CTA
		val monthFormatter = remember { DateTimeFormatter.ofPattern("MMM–yyyy", Locale.getDefault()) }
		val now = remember { YearMonth.now() }
		val months = remember { listOf(now, now.minusMonths(1), now.minusMonths(2)) }
		var selectedMonth by remember { mutableStateOf<YearMonth?>(months.first()) }

		Card(
			shape = RoundedCornerShape(16.dp),
			colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
			elevation = CardDefaults.cardElevation(6.dp),
			modifier = Modifier.fillMaxWidth()
		) {
			Column(modifier = Modifier.padding(16.dp)) {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Column(modifier = Modifier.weight(1f)) {
						Text(text = "Total Earnings", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
						Spacer(Modifier.height(6.dp))
						Text(text = "₹15,800", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
					}
                    CustomDropdownMenu(
						label = monthFormatter.format(months.first()),
						options = months,
						selectedOption = selectedMonth,
						onOptionSelected = { selectedMonth = it },
                        optionLabel = { it.format(monthFormatter) },
                        dropdownHeight = 150.dp,
                        fieldModifier = Modifier.width(160.dp)
					)
				}
				Spacer(Modifier.height(12.dp))
				CustomButton(
					onClick = { /* TODO: navigate to earnings details */ },
					shape = RoundedCornerShape(10.dp),
					text = "View Details",
				)
			}
		}

		Spacer(Modifier.height(20.dp))

		Surface(
			shape = RoundedCornerShape(12.dp),
			tonalElevation = 2.dp,
			shadowElevation = 4.dp,
			modifier = Modifier.fillMaxWidth()
		) {
			Column(modifier = Modifier.fillMaxWidth()) {
				ProfileMenuItem(
					icon = Icons.Outlined.CalendarToday,
					label = "Mark Attendance",
					onClick = { /* TODO */ }
				)
				HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
				ProfileMenuItem(
					icon = Icons.Outlined.TaskAlt,
					label = "Apply Leave",
					onClick = { /* TODO */ }
				)
				HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
				ProfileMenuItem(
					icon = Icons.Outlined.VolunteerActivism,
					label = "Refer and Earn",
					onClick = { /* TODO */ }
				)
				HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
				ProfileMenuItem(
					icon = Icons.Outlined.LocalPhone,
					label = "Contact Team",
					onClick = { /* TODO */ }
				)
				HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
				ProfileMenuItem(
					icon = Icons.Outlined.Download,
					label = "Download ID",
					onClick = { /* TODO */ }
				)
				HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
				ProfileMenuItem(
					icon = Icons.AutoMirrored.Outlined.Logout,
					label = "LogOut",
					onClick = { /* TODO */ }
				)
			}
		}
	}
}

@Composable
private fun ProfileMenuItem(
	icon: androidx.compose.ui.graphics.vector.ImageVector,
	label: String,
	onClick: () -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() }
			.padding(horizontal = 16.dp, vertical = 14.dp)
	) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			Icon(imageVector = icon, contentDescription = label, tint = MaterialTheme.colorScheme.onSurfaceVariant)
			Spacer(Modifier.width(12.dp))
			Text(text = label, style = MaterialTheme.typography.bodyLarge)
		}
		Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "Go", tint = MaterialTheme.colorScheme.onSurfaceVariant)
	}
}