package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content

import android.Manifest
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.content.FileProvider
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomButton
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomTextField
import com.example.pikndelappkotlin.utils.permissions.rememberPermissionGate
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PickupContent() {
    val context = LocalContext.current
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
    var currentPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher to capture a photo and write into a provided Uri
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            capturedImageUri = currentPhotoUri
        }
    }

    fun openCamera() {
        val uri = createImageUri(context)
        currentPhotoUri = uri
        takePictureLauncher.launch(uri)
    }

    // Reusable permission gate from AppPermissions: check+request CAMERA then run openCamera()
    val askCameraAndOpen = rememberPermissionGate(
        permission = Manifest.permission.CAMERA,
        onGranted = { openCamera() }
    )

    val vehicleOptions = remember {
        listOf(
            "MH04KU0732 (BALAJI N SUTRAVE)",
            "MH47AS8378 (BALAJI N SUTRAVE)",
            "MH46BU4032 (BALAJI N SUTRAVE)",
            "MH04JU1894 (BALAJI N SUTRAVE)",
            "MH48AY6159 (BALAJI N SUTRAVE)",
            "MH04JU1894 (BALAJI N SUTRAVE)",
            "MH48AY6159 (BALAJI N SUTRAVE)",
            "MH02FG6694 (SELF)",
            "Other (Self)"
        )
    }
    var selectedVehicle by remember { mutableStateOf<String?>(null) }
    var driverName by remember { mutableStateOf("") }
    var meterReading by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Start Your Trip",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Vehicle dropdown
        CustomDropdownMenu(
            label = "---Please Select Vehicle---",
            options = vehicleOptions,
            selectedOption = selectedVehicle,
            onOptionSelected = { selectedVehicle = it },
            optionLabel = { it }
        )


        CustomTextField(
            value = driverName,
            onValueChange = { driverName = it },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "Driver name",
            imeAction = ImeAction.Next,

        )


        // Meter reading (required)
        CustomTextField(
            value = meterReading,
            onValueChange = { meterReading = it },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "Meter reading",
            keyboardType = KeyboardType.Number,
            onlyNumbers = true,

            )


        Spacer(modifier = Modifier.height(10.dp))

        // Upload meter image
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Upload Meter Image: ")
                Text(text = "*", color = Color.Red)
            }
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xffb1b1b1),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { askCameraAndOpen() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = "Upload",
                    tint = Color(0xFFBDBDBD),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Primary CTA
        CustomButton(
            text = "Start your trip",
            icon =Icons.Filled.LocalShipping ,
            onClick = { /* TODO: handle next */ },
            isIcon = true
        )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PickupContentPreview() {
    PickupContent()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomDropdownMenu(
    label: String,
    options: List<T>,
    selectedOption: T?,
    onOptionSelected: (T) -> Unit,
    optionLabel: (T) -> String = { it.toString() },
    dropdownHeight: Dp = 300.dp,
    fieldModifier: Modifier = Modifier.fillMaxWidth(),
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        CustomTextField(
            value = selectedOption?.let { optionLabel(it) } ?: "",
            onValueChange = {},
            placeholderText = label,
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                .height(50.dp)
                .then(fieldModifier)
                .clickable { expanded = true },
            isEditable = false,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },

        )
//        OutlinedTextField(
//            value = selectedOption?.let { optionLabel(it) } ?: "",
//            onValueChange = {},
//            shape = RoundedCornerShape(10.dp),
//            modifier = Modifier
//                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
//                .height(50.dp)
//                .then(fieldModifier)
//                .clickable { expanded = true },
//            placeholder = {
//                Text(text = label)
//            },
//            textStyle = TextStyle(
//                color = Color.DarkGray,
//                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
//                fontWeight = FontWeight.Normal
//            ),
//            readOnly = true,
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//            },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = Color(0xffb1b1b1),
//                unfocusedBorderColor = Color(0xffb1b1b1),
//                focusedTrailingIconColor = Color(0xffb1b1b1),
//                unfocusedTrailingIconColor = Color(0xffb1b1b1),
//                focusedLeadingIconColor = Color(0xffb1b1b1),
//                unfocusedLeadingIconColor = Color(0xffb1b1b1),
//                focusedLabelColor = Color.DarkGray,
//                unfocusedLabelColor = Color.DarkGray,
//                cursorColor = Color.DarkGray,
//            )
//        )

        ExposedDropdownMenu(
            modifier = Modifier.height(dropdownHeight),
            shape = RoundedCornerShape(10.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).padding(horizontal = 10.dp),
                    text = { Text(optionLabel(option)) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }

        }
    }

}

private fun createImageUri(context: android.content.Context): Uri {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File(storageDir, "IMG_${timeStamp}.jpg")
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )
}
