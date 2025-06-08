package com.swalisoft.payer.core.composable.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabeledIconButton(
  text: StringResource,
  onClick: () -> Unit,
  icon: ImageVector,
  isVisible: Boolean = true,
  reverse: Boolean = true,
) {
  Box(
    modifier = Modifier
      .then(
        if (isVisible) Modifier else Modifier.layout { _, _ ->
          layout(width = 180, height = 0) {}
        }
      ).clip(RoundedCornerShape(15.dp))
      .clickable(onClick = onClick)
      .padding(8.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      if (reverse) {
        Text(
          text = stringResource(text),
          style = MaterialTheme.typography.labelSmall
        )
        Icon(
          imageVector = icon,
          contentDescription = stringResource(text),
        )
      } else {
        Icon(
          imageVector = icon,
          contentDescription = stringResource(text),
        )
        Text(
          text = stringResource(text),
          style = MaterialTheme.typography.labelSmall
        )
      }
    }
  }
}
