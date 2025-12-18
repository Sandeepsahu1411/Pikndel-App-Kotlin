package com.example.pikndelappkotlin.presentation.screens.bottom_bar_screens.home_tabs_content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pikndelappkotlin.presentation.screens.common_composable.CustomQrScanner
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private data class ScanRow(val podNo: String, val boxNo: String, val message: String)



@Composable
fun BoxScanContent() {
    // Step 1 -> scan POD, Step 2 -> scan BOX
    var step by remember { mutableIntStateOf(1) }
    var scannedPod by remember { mutableStateOf<String?>(null) }
    var scannedBox by remember { mutableStateOf<String?>(null) }
    val scannedList = remember { mutableStateListOf<ScanRow>() }
    var lastValue by remember { mutableStateOf<String?>(null) }
    var canScan by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 0.dp)
    ) {
        Text(
            text = if (step == 1) "1- Scan Pod QR" else "2- Scan Box QR",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        CustomQrScanner(
            onToggleFlash = { /* will be wired when camera added */ },
            onToggleExpand = { /* expand not implemented in this UI-only pass */ },
            onBarcodeScanned = { code ->
                if (!canScan) return@CustomQrScanner
                // ignore duplicates back-to-back
                if (code == lastValue) return@CustomQrScanner

                if (step == 1) {
                    scannedPod = code
                    lastValue = code
                    canScan = false
                    scope.launch {
                        delay(2000) // wait 2 seconds before allowing second scan
                        canScan = true
                        step = 2
                    }
                } else {
                    if (code == scannedPod) return@CustomQrScanner
                    // Show Box No immediately, then wait 2s before listing and clearing
                    scannedBox = code
                    lastValue = code
                    canScan = false
                    scope.launch {
                        delay(2000)
                        val pod = scannedPod.orEmpty()
                        val box = scannedBox.orEmpty()
                        val msg = if (pod.take(3) != box.take(3)) "Invalid POD No!" else "Scanned"
                        scannedList.add(0, ScanRow(pod, box, msg))
                        scannedPod = null
                        scannedBox = null
                        lastValue = null
                        canScan = true
                        step = 1
                    }
                }
            }
        )

        Spacer(Modifier.height(8.dp))

        // Status row: "POD No : ..."  "Box No : ..."
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "POD No : " + (scannedPod ?: "Not Scan !"),
                style = MaterialTheme.typography.bodyMedium,
                color = if (scannedPod == null) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Box No : " + (scannedBox ?: "Not Scan !"),
                style = MaterialTheme.typography.bodyMedium,
                color = if (scannedBox == null) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(Modifier.height(10.dp))

        // Header row
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 2.dp,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    "Pod No",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Box No",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "Message",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(scannedList) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        row.podNo,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        row.boxNo,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                    val isError = row.message.contains("invalid", ignoreCase = true)
                    Text(
                        row.message,
                        color = if (isError) Color(0xFFD32F2F) else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xFFE0E0E0))
                )
            }
        }
    }
}