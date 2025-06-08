package com.swalisoft.payer.ocr.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun ImagePicker(onImagePicked: (ByteArray) -> Unit)