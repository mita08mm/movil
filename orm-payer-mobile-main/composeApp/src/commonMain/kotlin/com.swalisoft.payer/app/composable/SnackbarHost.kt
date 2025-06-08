package com.swalisoft.payer.app.composable

import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.swalisoft.payer.core.composable.ToastAlert
import com.swalisoft.payer.core.composable.ToastVariant
import eus.ctb.barik.common.helper.ToastManagerHelper
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

@Composable
fun SnackbarHost() {
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()
  var toastVariant: ToastVariant = ToastVariant.Disabled

  LaunchedEffect(Unit) {
    ToastManagerHelper.shared.addListener { message, variant, formatArg ->
      toastVariant = variant
      scope.launch {
        if (message is String) {
          snackbarHostState.showSnackbar(message)
        } else if (message is StringResource) {
          snackbarHostState.showSnackbar(getString(message, *formatArg))
        }
      }
    }
  }

  SnackbarHost(
    hostState = snackbarHostState,
  ) { snackbarData: SnackbarData ->
    ToastAlert(
      snackbarData.visuals.message,
      variant = toastVariant,
      onDismiss = { snackbarData.dismiss() },
    )
  }
}

