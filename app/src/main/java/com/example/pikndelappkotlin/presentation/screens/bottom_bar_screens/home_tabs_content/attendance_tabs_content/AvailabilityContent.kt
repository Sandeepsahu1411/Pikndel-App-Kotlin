package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content

import android.R.attr.end
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pikndelappkotlin.presentation.components.CommonSelectionDialog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AvailabilityType(modifier: Modifier = Modifier) {
    AvailabilityContent()
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AvailabilityContent(modifier: Modifier = Modifier) {

    val dates = remember { generateNextSevenDays(LocalDate.now()) }
    val dateToSelection = remember { mutableStateMapOf<LocalDate, AvailabilityType>() }

    var dialogDate by remember { mutableStateOf<LocalDate?>(null) }
    var dialogTempSelection by remember { mutableStateOf<AvailabilityType?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Date",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 30.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Availability",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.size(5.dp))

        LazyColumn {
            items(dates) { date ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = date.format(uiDateFormatter),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )

                    val currentSelection = dateToSelection[date]
                    val isSelected = currentSelection != null
                    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    val textColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                dialogDate = date
                                dialogTempSelection = currentSelection
                            }
                            .weight(1f)

                    ) {
                        Text(
                            text = currentSelection?.displayName ?: "Select",
                            modifier = Modifier
                                .weight(1f)
                                .padding((8.dp))
                            ,
                            color = textColor
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 5.dp)
                            ,
                            tint = textColor
                        )
                    }
                }
            }
        }

        if (dialogDate != null) {
            CommonSelectionDialog(
                title = "Availability",
                leadingIcon = Icons.Outlined.CheckCircle,
                options = AvailabilityType.allDisplayNames,
                selectedOption = dialogTempSelection?.displayName,
                onOptionSelected = { display ->
                    dialogTempSelection = AvailabilityType.fromDisplayName(display)
                },
                onDone = {
                    dialogDate?.let { date ->
                        dialogTempSelection?.let { selection ->
                            dateToSelection[date] = selection
                        }
                    }
                    dialogDate = null
                    dialogTempSelection = null
                },
                onDismiss = {
                    dialogDate = null
                    dialogTempSelection = null
                }
            )
        }
    }
}

// --- In-file small utilities and types ---

enum class AvailabilityType(val displayName: String) {
    NOT_AVAILABLE("Not Available"),
    FULL_DAY("Full Day"),
    FIRST_HALF("First Half"),
    SECOND_HALF("Second Half");

    companion object {
        val allDisplayNames: List<String>
            get() = entries.map { it.displayName }

        fun fromDisplayName(name: String?): AvailabilityType? {
            if (name == null) return null
            return entries.firstOrNull { it.displayName == name }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateNextSevenDays(startDate: LocalDate = LocalDate.now()): List<LocalDate> {
    return (0 until 7).map { startDate.plusDays(it.toLong()) }
}

@RequiresApi(Build.VERSION_CODES.O)
val uiDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")


