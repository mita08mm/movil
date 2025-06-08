package com.swalisoft.payer.ocr.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp


@Composable
expect fun ActualCameraPreview(onImageCaptured: (ByteArray) -> Unit)

@Composable
fun CameraPreview(onCapture: (ByteArray) -> Unit) {
    var showPermissionRequester by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        } else {
            ActualCameraPreview(onImageCaptured = { imageBytes ->
                loading = true
                onCapture(imageBytes)
            })
        }
    }
}