package com.swalisoft.payer.core.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun InAppWebView(
  content: String,
  modifier: Modifier,
  onLoaded: () -> Unit,
) {
}