package com.swalisoft.payer.core.locale

enum class AppLang(
  val code: String,
  val string: String,
  val decimalSeparator: Char,
  val thousandSeparator: Char,
) {
  Spanish("es", "Es", ',', '.'),
  Euskera("eu", "Eu", ',', '.'),
  English("en", "En", '.', ','),
}

fun AppLang.toCountryLanguage(): String {
  var response = code + "_ES";

  if (code == "en") {
    response = code + "_US"
  }

  return response;
}
