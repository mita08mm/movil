package com.swalisoft.payer.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.swalisoft.payer.app.AppColorSchema

@Composable
fun GradientButton(
  text: String,
  modifier: Modifier = Modifier,
  onClick: () -> Unit,
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(50))
      .background(brush = AppColorSchema.PrimaryGradient)
      .clickable(onClick = onClick)
      .padding(horizontal = 32.dp, vertical = 14.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text,
      color = Color.White,
      fontWeight = FontWeight.Bold,
      style = MaterialTheme.typography.bodyLarge
    )
  }
}
