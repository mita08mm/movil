package com.swalisoft.payer.core.locale

import kotlin.math.pow

fun Float.localeFormat(locale: AppLang, decimals: Int = 2): String {
  var result = ""

  val extendNumber = (10.0).pow(decimals)
  val rounded = (this * extendNumber).toInt().toString()

  if(this.toInt() == 0) {
    return "0${locale.decimalSeparator}0";
  }

  val limit: Int = rounded.length - 1
  val decimalLimit: Int = rounded.length - decimals

  for(i: Int in limit downTo 0) {
    val num: Char = rounded[i]

    result = if(decimalLimit == i) {
      "${locale.decimalSeparator}$num$result"
    } else if((rounded.length - decimals - i) % 3 == 0 && i > decimals) {
      "${locale.thousandSeparator}$num$result"
    } else {
      num + result
    }
  }

  return result
}
