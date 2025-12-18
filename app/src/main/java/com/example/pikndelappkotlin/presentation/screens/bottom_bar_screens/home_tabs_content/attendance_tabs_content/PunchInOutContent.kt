package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content

import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomButton
import com.example.pikndelappkotlin.utils.permissions.rememberPermissionsGate
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PunchInOutContent(
    modifier: Modifier = Modifier,
    startTime: String? = null,
    endTime: String? = null
) {
    val date = remember {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Today, Date
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Today, ",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Two Elevated Cards: Day start / Day end
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TimeCard(
                title = "Day start",
                time = startTime ?: "00:00:00",
                modifier = Modifier.weight(1f)
            )
            TimeCard(
                title = "Day end",
                time = endTime ?: "00:00:00",
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Map Area (with elevation)
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF3F5F7))
            ) {
                MapWithCurrentLocation()
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        CustomButton(
            text = "Punch In/Out",
            modifier = Modifier.fillMaxWidth(),
            onClick = { /* TODO */ },
            containerColor = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(5.dp))

        CustomButton(
            text = "Apply Leave",
            modifier = Modifier.fillMaxWidth(),
            onClick = { /* TODO */ },
            containerColor = MaterialTheme.colorScheme.primary,
            icon = Icons.Outlined.EditCalendar,
            isIcon = true
        )

    }
}

@Composable
private fun TimeCard(title: String, time: String, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier, elevation = CardDefaults.cardElevation(8.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            Text(
                text = time,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun MapWithCurrentLocation() {
    val context = LocalContext.current
    val cameraState: CameraPositionState = rememberCameraPositionState()
    var currentLatLng by remember { mutableStateOf<LatLng?>(null) }
    var locationGranted by remember { mutableStateOf(false) }

    // Reusable gate: ask FINE+COARSE then center camera
    val askLocation = rememberPermissionsGate(
        permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        onGrantedAll = {
            locationGranted = true
        },
        onDenied = {
            locationGranted = false
        }
    )

    LaunchedEffect(Unit) { askLocation() }

    val fusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(locationGranted) {
        if (locationGranted) {
            scope.launch {
                currentLatLng = moveCameraToCurrentLocation(fusedClient, cameraState)
            }
        }
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(450.dp),
        cameraPositionState = cameraState,
        properties = MapProperties(isMyLocationEnabled = locationGranted),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true, zoomControlsEnabled = false)
    ) {
        // Fixed marker at user's current location (green pin)
        currentLatLng?.let { latLng ->
            Marker(
                state = MarkerState(position = latLng),
                title = "You",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        }
    }
}

@SuppressLint("MissingPermission")
private suspend fun moveCameraToCurrentLocation(
    fusedClient: FusedLocationProviderClient,
    cameraState: CameraPositionState
): LatLng? {
    // If lastLocation is null, request a single high-accuracy update
    val last = getLastKnownLocation(fusedClient)
    val location =
        last ?: fusedClient.awaitSingleHighAccuracyLocation(timeoutMillis = 7_000L) ?: return null
    val latLng = LatLng(location.latitude, location.longitude)
    cameraState.centerOn(latLng, zoom = 16f)
    return latLng
}

// --- Small helpers for location + camera ---
@SuppressLint("MissingPermission")
private suspend fun FusedLocationProviderClient.awaitSingleHighAccuracyLocation(
    timeoutMillis: Long
): android.location.Location? = withTimeoutOrNull(timeoutMillis) {
    kotlinx.coroutines.suspendCancellableCoroutine { cont ->
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1_000L)
            .setMaxUpdates(1)
            .build()
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                removeLocationUpdates(this)
                if (!cont.isCompleted) cont.resume(result.lastLocation)
            }
        }
        requestLocationUpdates(request, callback, Looper.getMainLooper())
        cont.invokeOnCancellation { removeLocationUpdates(callback) }
    }
}

@SuppressLint("MissingPermission")
private suspend fun getLastKnownLocation(
    fusedClient: FusedLocationProviderClient
): android.location.Location? = kotlinx.coroutines.suspendCancellableCoroutine { cont ->
    fusedClient.lastLocation
        .addOnSuccessListener { cont.resume(it) }
        .addOnFailureListener { cont.resume(null) }
}

private suspend fun CameraPositionState.centerOn(latLng: LatLng, zoom: Float) {
    animate(
        update = CameraUpdateFactory.newLatLngZoom(latLng, zoom),
        durationMs = 800
    )
}