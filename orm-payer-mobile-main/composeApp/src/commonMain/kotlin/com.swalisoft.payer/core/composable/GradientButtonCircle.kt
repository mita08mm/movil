package com.swalisoft.payer.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun GradientButtonCircle(
  icon: ImageVector,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  onClick: () -> Unit,
) {
  val gradient = Brush.linearGradient(
    colors = listOf(Color(0xFF01F0FF), Color(0xFF4441ED)),
    start = Offset(0f, 0f),
    end = Offset(180f, 180f),
  )

  Box(
    modifier = modifier
      .clip(CircleShape)
      .background(brush = gradient)
      .clickable(onClick = onClick, enabled = enabled)
      .padding(16.dp),
    contentAlignment = Alignment.Center
  ) {
    Icon(imageVector = icon, contentDescription = null, tint = Color.White)
  }
}
