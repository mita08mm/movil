package com.swalisoft.payer.app

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import payer.composeapp.generated.resources.Res
import payer.composeapp.generated.resources.roboto_black
import payer.composeapp.generated.resources.roboto_bold
import payer.composeapp.generated.resources.roboto_light
import payer.composeapp.generated.resources.roboto_medium
import payer.composeapp.generated.resources.roboto_regular
import payer.composeapp.generated.resources.roboto_thin
import org.jetbrains.compose.resources.Font

@Composable
fun AppTypography(): Typography {
  val family = FontFamily(
    Font(Res.font.roboto_thin, weight = FontWeight.Thin),
    Font(Res.font.roboto_light, weight = FontWeight.Light),
    Font(Res.font.roboto_regular, weight = FontWeight.Normal),
    Font(Res.font.roboto_medium, weight = FontWeight.Medium),
    Font(Res.font.roboto_bold, weight = FontWeight.Bold),
    Font(Res.font.roboto_black, weight = FontWeight.Black),
  )

  val typography = Typography(
    bodySmall = TextStyle(
      fontFamily = family,
      fontSize = 12.sp,
    ),
    bodyMedium = TextStyle(
      fontFamily = family,
      fontSize = 14.sp,
      lineHeight = 20.sp,
    ),
    bodyLarge = TextStyle(
      fontFamily = family,
      fontSize = 16.sp,
    ),
    headlineLarge = TextStyle(
      fontFamily = family,
    ),
    displaySmall = TextStyle(
      fontFamily = family,
      fontSize = 24.sp,
    ),
    displayMedium = TextStyle(
      fontFamily = family,
      fontSize = 32.sp,
      fontWeight = FontWeight.Bold,
    ),
    titleMedium = TextStyle(
      fontFamily = family,
      fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
      fontFamily = family,
    ),
    titleLarge = TextStyle(
      fontFamily = family,
      fontSize = 20.sp,
    ),
  )

  return typography
}
