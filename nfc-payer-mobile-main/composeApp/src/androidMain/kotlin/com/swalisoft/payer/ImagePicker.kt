package com.swalisoft.payer.ocr.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import android.util.Log

@Composable
actual fun ImagePicker(onImagePicked: (ByteArray) -> Unit) {
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bytes = inputStream.readBytes()

                    onImagePicked(bytes)
                }
            } catch (e: Exception) {
                Log.e("PhotoPicker", "Error reading image", e)
            }
        } else {
            onImagePicked(ByteArray(0))

            Log.d("PhotoPicker", "No media selected")
        }
    }

    LaunchedEffect(Unit) {
        // Launch the photo picker with image/* MIME type
        pickMedia.launch("image/*")
    }

} 