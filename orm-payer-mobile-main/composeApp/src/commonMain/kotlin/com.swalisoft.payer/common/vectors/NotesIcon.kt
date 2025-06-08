package com.swalisoft.payer.common.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Vectors.Group2: ImageVector
    get() {
        if (_group2 != null) {
            return _group2!!
        }
        _group2 = Builder(name = "Group2", defaultWidth = 44.0.dp, defaultHeight = 41.0.dp,
                viewportWidth = 44.0f, viewportHeight = 41.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeAlpha = 0.74f, strokeLineWidth = 1.5f, strokeLineCap = Round,
                    strokeLineJoin = Miter, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(21.776f, 16.586f)
                lineTo(32.04f, 19.12f)
                moveTo(20.125f, 22.261f)
                lineTo(26.283f, 23.781f)
                moveTo(39.913f, 21.769f)
                curveTo(38.627f, 26.185f, 37.985f, 28.394f, 36.53f, 29.826f)
                curveTo(35.381f, 30.956f, 33.895f, 31.747f, 32.258f, 32.099f)
                curveTo(32.053f, 32.144f, 31.844f, 32.178f, 31.632f, 32.201f)
                curveTo(29.687f, 32.423f, 27.313f, 31.837f, 22.996f, 30.772f)
                curveTo(18.204f, 29.587f, 15.807f, 28.996f, 14.253f, 27.654f)
                curveTo(13.026f, 26.595f, 12.168f, 25.225f, 11.786f, 23.716f)
                curveTo(11.302f, 21.805f, 11.943f, 19.597f, 13.229f, 15.182f)
                lineTo(14.328f, 11.398f)
                lineTo(14.846f, 9.626f)
                curveTo(15.813f, 6.363f, 16.463f, 4.567f, 17.711f, 3.339f)
                curveTo(18.86f, 2.21f, 20.346f, 1.419f, 21.982f, 1.068f)
                curveTo(24.056f, 0.621f, 26.453f, 1.213f, 31.247f, 2.397f)
                curveTo(36.037f, 3.58f, 38.434f, 4.172f, 39.987f, 5.511f)
                curveTo(41.214f, 6.571f, 42.073f, 7.942f, 42.454f, 9.451f)
                curveTo(42.796f, 10.803f, 42.575f, 12.303f, 41.963f, 14.627f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeAlpha = 0.74f, strokeLineWidth = 1.5f, strokeLineCap = Round,
                    strokeLineJoin = StrokeJoin.Companion.Round, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(3.703f, 29.603f)
                curveTo(4.986f, 34.019f, 5.63f, 36.228f, 7.086f, 37.66f)
                curveTo(8.234f, 38.79f, 9.721f, 39.581f, 11.357f, 39.933f)
                curveTo(13.431f, 40.378f, 15.828f, 39.786f, 20.622f, 38.603f)
                curveTo(25.412f, 37.421f, 27.809f, 36.829f, 29.362f, 35.488f)
                curveTo(30.407f, 34.586f, 31.187f, 33.456f, 31.632f, 32.202f)
                moveTo(14.846f, 9.624f)
                curveTo(14.099f, 9.803f, 13.274f, 10.005f, 12.37f, 10.231f)
                curveTo(7.579f, 11.414f, 5.182f, 12.005f, 3.628f, 13.345f)
                curveTo(2.401f, 14.404f, 1.542f, 15.776f, 1.161f, 17.285f)
                curveTo(0.819f, 18.636f, 1.04f, 20.136f, 1.652f, 22.461f)
            }
        }
        .build()
        return _group2!!
    }

private var _group2: ImageVector? = null
