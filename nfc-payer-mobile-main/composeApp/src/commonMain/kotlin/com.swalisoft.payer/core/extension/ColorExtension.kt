package com.swalisoft.payer.core.extension

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

fun Color.blendColor(foreground: Color): Color {
  val alpha = foreground.alpha

  fun blendChannel(fg: Float, bg: Float): Int = ((fg * alpha + bg * (1 - alpha)) * 255).roundToInt()

  val r = blendChannel(foreground.red, this.red)
  val g = blendChannel(foreground.green, this.green)
  val b = blendChannel(foreground.blue, this.blue)

  return Color(r, g, b)
}
