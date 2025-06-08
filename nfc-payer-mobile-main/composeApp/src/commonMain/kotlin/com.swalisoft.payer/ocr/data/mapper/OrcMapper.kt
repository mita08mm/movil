package com.swalisoft.payer.ocr.data.mapper

import com.swalisoft.payer.ocr.domain.OcrModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
  @SerialName("message") val message: String,
)

@Serializable
data class ApiConflictResponse(
  @SerialName("message") val message: List<String>,
)

@Serializable
data class OrcRequestDto(
  val image: ByteArray,
  @SerialName("categoryId") val categoryId: String,
  val imageName: String,
)

fun OcrModel.toDto() = OrcRequestDto(
  image = image,
  categoryId = categoryId,
  imageName = imageName ?: "ocr_image.jpg",
)
