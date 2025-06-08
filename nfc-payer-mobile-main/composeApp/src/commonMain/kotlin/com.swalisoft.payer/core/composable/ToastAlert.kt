package com.swalisoft.payer.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swalisoft.payer.core.extension.blendColor

enum class ToastVariant {
  Disabled,
  Success,
  Warning,
  Error,
}

@Composable
fun ToastAlert(
  message: String,
  variant: ToastVariant = ToastVariant.Success,
  onDismiss: () -> Unit,
) {
  val color = when (variant) {
    ToastVariant.Disabled -> Color.Gray
    ToastVariant.Success -> Color.LightGray
    ToastVariant.Warning -> Color.Yellow
    ToastVariant.Error -> Color.Red
  }

  ElevatedCard(
    modifier = Modifier
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .border(1.dp, color, CardDefaults.elevatedShape),
  ) {
    Row(
      Modifier.height(IntrinsicSize.Min).fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Box(Modifier.fillMaxHeight().width(8.dp).background(color))

      Text(
        message,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
          .weight(2f)
          .padding(
            top = 20.dp,
            bottom = 20.dp,
            start = 16.dp,
            end = 4.dp,
          ),
      )

      IconButton(
        onClick = onDismiss
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = null,
        )
      }
    }
  }
}
