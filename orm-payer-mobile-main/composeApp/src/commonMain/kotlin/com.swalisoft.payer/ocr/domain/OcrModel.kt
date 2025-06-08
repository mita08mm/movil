package com.swalisoft.payer.ocr.domain

data class OcrModel (
  val image: ByteArray,
  val categoryId: String,
  val imageName: String?,
)
