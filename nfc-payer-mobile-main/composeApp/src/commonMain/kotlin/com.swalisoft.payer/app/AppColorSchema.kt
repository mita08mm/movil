package com.swalisoft.payer.app

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import kotlin.jvm.JvmInline
import androidx.compose.ui.geometry.Offset

@JvmInline
/**
 * A class to contain colors used in this project
 */
value class AppColorSchema(val value: ULong) {
    companion object {
        private val Primary = Color(0xFF19AFF8)

        val CbtRed = Color(0xFFE3312C)

        val CtbRedLight = Color(0xFFFCEAEA)

        /** #E3312C */
        val MainRed = Color(0xFFE3312C)
        val DarkRed = Color(0xFF8A1E1B)

        /** #FCEAEA */
        val LightRed = Color(0xFFFCEAEA)

        /** #454A4B */
        val MainGrey = Color(0xFF242B38)

        /** #939B9C */
        val SecondaryGrey = Color(0xFF939B9C)

        /** #94A1A3 */
        val Unselected = Color(0xFF94A1A3)

        /** #D9D9D9 */
        val SecondaryButton = Color(0xFFD9D9D9)

        /** #E8E8E8 */
        val Border = Color(0xFFE8E8E8)

        /** #F0F0F0 */
        val LightGrey = Color(0xFFD9D9D9)

        val TextSubtlest = Color(0xFF6E737C)
        val SurfacePageSubtle: Color = Color(0xFFE3E3E5)

        val ChipGrey = Color(0xFFE8E8E8)

        /** #4CA11E */
        val Green = Color(0xFF4CA11E)

        /** #D6EEC8 */
        val LightGreen = Color(0xFF83D35E)

        /** #F4BD00 */
        val Yellow = Color(0xFFF4BD00)

        /** #FEF8E6 */
        val LightWarning = Color(0xFFFEF8E6)
        /** Default light theme for project */

        /** #6E737C */
        val Subtlest = Color(0xFF6E737C)

        /** #242B38 */
        val TextDefault = Color(0xFF242B38)

        val SuccessMid = Color(0xFF5C9442)

        val AlmostNegative: Color = Color(0xFFF6F7F7)

        val IconClearer: Color = Color(0xFFA3A6AB)

        /** State Colors */
        val Error = Color(0xFFE3312C)
        val Warning = Color(0xFFAB8400)
        val Disabled = Color(0xFF939B9C)

        val ErrorContrast = Color(0xFFFCEAEA)
        val WarningContrast = Color(0xFFFEF8E6)
        val DisabledContrast = Color(0xFFEBECED)

        /** InputField **/
        val PlaceholderColor = Color(0xFF8E8E93)
        val BorderColor = Color(0xFF48484A)

        val Background = Color(0xFF202326)
        val Surface = Color(0xFF2F3235)

        val PrimaryGradient = Brush.linearGradient(
            colors = listOf(Color(0xFF01F0FF), Color(0xFF4441ED)),
            start = Offset(0f, 0f),
            end = Offset(600f, 600f),
        )

        val darkColors = darkColorScheme(
            primary = Primary,
            onPrimary = Color.White,

//            secondary = CtbRedLight,
//            onSecondary = Color.White,

            background = Background,
            onBackground = Color(0xFFD3D4DD),

            primaryContainer = Primary,

            surfaceContainer = Surface,
            surfaceContainerHigh = Surface,

            surface = Surface,
            onSurface = Color.White,

            surfaceVariant = Surface,
            onSurfaceVariant = Color.White,

            surfaceContainerLow = Surface,

            surfaceContainerHighest = Surface,
            surfaceContainerLowest = Surface,
        )
    }
}