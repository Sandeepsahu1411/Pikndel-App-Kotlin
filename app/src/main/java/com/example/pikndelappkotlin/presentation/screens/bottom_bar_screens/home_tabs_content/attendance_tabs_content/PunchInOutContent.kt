package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content.attendance_tabs_content

import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
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
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Today, Date
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
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

        // Two Elevated Cards: Day start / Day end
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TimeCard(
                title = "Day start",
                time = startTime ?: "--:--:--",
                modifier = Modifier.weight(1f)
            )
            TimeCard(
                title = "Day end",
                time = endTime ?: "00:00:00",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        // Map Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF3F5F7))

        ) {
            MapWithCurrentLocation()
        }
    }
}

@Composable
private fun TimeCard(title: String, time: String, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
            Spacer(Modifier.height(4.dp))
            Text(
                text = time,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun  MapWithCurrentLocation() {
    val context = LocalContext.current
    val cameraState: CameraPositionState = rememberCameraPositionState()
    var currentLatLng by remember { mutableStateOf<LatLng?>(null) }

    val permissions: MultiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        permissions.launchMultiplePermissionRequest()
    }

    val fusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(permissions.allPermissionsGranted) {
        if (permissions.allPermissionsGranted) {
            scope.launch {
                currentLatLng = moveCameraToCurrentLocation(fusedClient, cameraState)
            }
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = MapProperties(isMyLocationEnabled = permissions.allPermissionsGranted),
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
) : LatLng? {
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