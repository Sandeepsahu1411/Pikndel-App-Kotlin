package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.report_tabs_content

import android.R.attr.fontWeight
import android.R.attr.text
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReportSummaryContent(modifier: Modifier = Modifier) {

	// Dummy values to mirror old app behaviour (API can wire here later)
	val progressPercent = 0.38f
	val targetPercent = 0.80f

	val todayLabel = remember {
		val today = LocalDate.now()
		val dayName = today.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
		val monthName = today.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
		"Today, $dayName, $monthName ${today.dayOfMonth}"
	}

	Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)) {
		Text(
			text = todayLabel,
			style = MaterialTheme.typography.titleMedium,
			fontWeight = FontWeight.Medium,
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier.padding(vertical =  12.dp)

		)

		ProgressWithTarget(
			progress = progressPercent,
			target = targetPercent,
			height = 20.dp
		)

		Spacer(modifier = Modifier.height(16.dp))

		Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
			MetricCard(
				title = "Attempt Pending",
				value = "01",
				valueColor = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.weight(1f)
			)
			MetricCard(
				title = "Delivered",
				value = "00",
				valueColor = Color(0xFF0DAA32),
				modifier = Modifier.weight(1f)
			)
		}

		Spacer(modifier = Modifier.height(12.dp))

		Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
			TwoStatCard(
				leftTitle = "Undel",
				leftValue = "00",
				leftColor = Color(0xFFE53935),
				rightTitle = "VeriUndel",
				rightValue = "00",
				rightColor = Color(0xFFE53935),
				modifier = Modifier.weight(1f)
			)
			MetricCard(
				title = "FE Handover Pending",
				value = "00",
				valueColor = Color(0xFFFFA000), // orange
				modifier = Modifier.weight(1f)
			)
		}

		Spacer(modifier = Modifier.height(20.dp))

		Text(
			text = "Pending COD",
			style = MaterialTheme.typography.titleMedium,
			fontWeight = FontWeight.Medium,
			color = MaterialTheme.colorScheme.onSurface
		)
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			text = "₹0",
			style = MaterialTheme.typography.headlineMedium,
			fontWeight = FontWeight.Bold,
			color = MaterialTheme.colorScheme.onSurface
		)
	}
}

@Composable
private fun MetricCard(
	title: String,
	value: String,
	valueColor: Color,
	modifier: Modifier = Modifier
) {
	ElevatedCard(modifier = modifier,
		elevation = CardDefaults.cardElevation(8.dp)) {
		Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
			Text(
				text = title,
				style = MaterialTheme.typography.bodySmall,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = value,
				color = valueColor,
				style = MaterialTheme.typography.headlineSmall.copy(letterSpacing = 0.sp),
				fontWeight = FontWeight.SemiBold
			)
		}
	}
}

@Composable
private fun TwoStatCard(
	leftTitle: String,
	leftValue: String,
	leftColor: Color,
	rightTitle: String,
	rightValue: String,
	rightColor: Color,
	modifier: Modifier = Modifier
) {
	ElevatedCard(modifier = modifier,
			elevation = CardDefaults.cardElevation(8.dp)) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.height(IntrinsicSize.Min)
				.padding(horizontal = 16.dp, vertical = 12.dp)
		) {
			Column(modifier = Modifier.weight(1f),horizontalAlignment = Alignment.CenterHorizontally) {
				Text(
					text = leftTitle,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = leftValue,
					color = leftColor,
					style = MaterialTheme.typography.headlineSmall,
					fontWeight = FontWeight.SemiBold
				)
			}
			Box(
				modifier = Modifier
					.width(2.dp)
					.fillMaxHeight()
					.background(MaterialTheme.colorScheme.outlineVariant)
			)
			Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
				Text(
					text = rightTitle,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = rightValue,
					color = rightColor,
					style = MaterialTheme.typography.headlineSmall,
					fontWeight = FontWeight.SemiBold
				)
			}
		}
	}
}

@Composable
private fun ProgressWithTarget(
	progress: Float,
	target: Float,
	height: Dp = 14.dp,
) {
	val progressAnim = remember { Animatable(0f) }
	val targetAnim = remember { Animatable(0f) }

	LaunchedEffect(progress) {
		progressAnim.snapTo(0f)
		progressAnim.animateTo(
			targetValue = progress.coerceIn(0f, 1f),
			animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
		)
	}
	LaunchedEffect(target) {
		targetAnim.snapTo(0f)
		targetAnim.animateTo(
			targetValue = target.coerceIn(0f, 1f),
			animationSpec = tween(durationMillis = 1200, easing = LinearOutSlowInEasing)
		)
	}

	BoxWithConstraints(modifier = Modifier.fillMaxWidth().height(40.dp)) {
		val barColor = Color(0xFF2DBF4E)
		val trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
		val targetColor = MaterialTheme.colorScheme.onSurfaceVariant
		val labelBelowColor = MaterialTheme.colorScheme.onSurfaceVariant

		val maxWidthDp = this.maxWidth
		val progressWidthDp = maxWidthDp * progressAnim.value
		val targetOffsetDp = maxWidthDp * target.coerceIn(0f, 1f)
		val progressCenterDp = (progressWidthDp / 2f)

		val density = LocalDensity.current
		var inBarLabelWidthDp by remember { mutableStateOf(0.dp) }
		var targetLabelWidthDp by remember { mutableStateOf(0.dp) }

		// Progress bar with target marker
		Box(modifier = Modifier
			.fillMaxWidth()
			.height(height)) {
			Canvas(modifier = Modifier.matchParentSize()) {
				// Track
				drawRoundRect(
					color = trackColor,
					size = size,
					cornerRadius = androidx.compose.ui.geometry.CornerRadius(x = size.height / 2f, y = size.height / 2f)
				)
				// Progress
				drawRoundRect(
					color = barColor,
					size = androidx.compose.ui.geometry.Size(width = size.width * progressAnim.value, height = size.height),
					cornerRadius = androidx.compose.ui.geometry.CornerRadius(x = size.height / 2f, y = size.height / 2f)
				)
				// Target mark
				val x = size.width * target.coerceIn(0f, 1f)
				drawLine(
					color = targetColor,
					start = androidx.compose.ui.geometry.Offset(x, 0f),
					end = androidx.compose.ui.geometry.Offset(x, size.height),
					strokeWidth = size.height * 0.12f,
					cap = StrokeCap.Round
				)
			}

			// 38% label centered inside the filled area
			Row(modifier = Modifier
				.fillMaxWidth()
				.height(height),
				verticalAlignment = Alignment.CenterVertically) {
				// compute left offset so that label's center aligns with progress center
				val safeLeftDp = (progressCenterDp - (inBarLabelWidthDp / 2))
					.coerceIn(0.dp, (maxWidthDp - inBarLabelWidthDp))
				Spacer(modifier = Modifier.width(safeLeftDp))
				Text(
					text = String.format(Locale.getDefault(), "%.2f%%", progressAnim.value * 100f),
					color = Color.White,
					style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
					modifier = Modifier
						.onGloballyPositioned {
							inBarLabelWidthDp = with(density) { it.size.width.toDp() }
						}
						.padding(horizontal = 4.dp),
					textAlign = TextAlign.Center
				)
			}
		}

		// 80% label just below the target line
		Row(modifier = Modifier.fillMaxWidth().offset(y = 23.dp)) {
			val safeLeftDp = (targetOffsetDp - (targetLabelWidthDp / 2))
				.coerceIn(0.dp, (maxWidthDp - targetLabelWidthDp))
			Spacer(modifier = Modifier.width(safeLeftDp))
			Box(
				modifier = Modifier
					.onGloballyPositioned {
						targetLabelWidthDp = with(density) { it.size.width.toDp() }
					}
					.background(
						color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
						shape = RoundedCornerShape(12.dp)
					)
			) {
				Text(
					text = String.format(Locale.getDefault(), "%.2f%%", targetAnim.value * 100f),
					color = labelBelowColor,
					style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
					modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
					textAlign = TextAlign.Center
				)
			}
		}
	}
}
