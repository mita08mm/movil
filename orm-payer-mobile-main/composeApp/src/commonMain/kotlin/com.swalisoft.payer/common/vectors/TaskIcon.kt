package com.swalisoft.payer.common.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Vectors.Vector1: ImageVector
    get() {
        if (_vector1 != null) {
            return _vector1!!
        }
        _vector1 = Builder(name = "Vector1", defaultWidth = 36.0.dp, defaultHeight = 41.0.dp,
                viewportWidth = 36.0f, viewportHeight = 41.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeAlpha = 0.68f, strokeLineWidth = 1.5f, strokeLineCap = Round,
                    strokeLineJoin = StrokeJoin.Companion.Round, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.333f, 25.188f)
                horizontalLineTo(26.0f)
                moveTo(9.333f, 17.688f)
                horizontalLineTo(13.5f)
                moveTo(9.333f, 32.688f)
                horizontalLineTo(17.667f)
                moveTo(13.5f, 4.563f)
                horizontalLineTo(5.167f)
                curveTo(4.062f, 4.563f, 3.002f, 4.958f, 2.22f, 5.661f)
                curveTo(1.439f, 6.364f, 1.0f, 7.318f, 1.0f, 8.313f)
                verticalLineTo(36.438f)
                curveTo(1.0f, 37.432f, 1.439f, 38.386f, 2.22f, 39.089f)
                curveTo(3.002f, 39.792f, 4.062f, 40.188f, 5.167f, 40.188f)
                horizontalLineTo(30.167f)
                curveTo(31.272f, 40.188f, 32.332f, 39.792f, 33.113f, 39.089f)
                curveTo(33.894f, 38.386f, 34.333f, 37.432f, 34.333f, 36.438f)
                verticalLineTo(8.313f)
                curveTo(34.333f, 7.318f, 33.894f, 6.364f, 33.113f, 5.661f)
                curveTo(32.332f, 4.958f, 31.272f, 4.563f, 30.167f, 4.563f)
                horizontalLineTo(22.875f)
                moveTo(13.5f, 4.563f)
                verticalLineTo(0.813f)
                moveTo(13.5f, 4.563f)
                verticalLineTo(8.313f)
            }
        }
        .build()
        return _vector1!!
    }

private var _vector1: ImageVector? = null
