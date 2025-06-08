package com.swalisoft.payer.common.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Vectors.KeyboardBackspace: ImageVector
  get() {
    if (_keyboardBackspace != null) {
      return _keyboardBackspace!!
    }
    _keyboardBackspace = Builder(
      name = "KeyboardBackspace", defaultWidth = 24.0.dp,
      defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
    ).apply {
      path(
        fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
        strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
        pathFillType = NonZero
      ) {
        moveTo(23.75f, 10.75f)
        horizontalLineTo(7.702f)
        lineTo(13.967f, 4.468f)
        lineTo(11.5f, 2.0f)
        lineTo(1.0f, 12.5f)
        lineTo(11.5f, 23.0f)
        lineTo(13.967f, 20.532f)
        lineTo(7.702f, 14.25f)
        horizontalLineTo(23.75f)
        verticalLineTo(10.75f)
        close()
      }
    }
      .build()
    return _keyboardBackspace!!
  }

private var _keyboardBackspace: ImageVector? = null
