package com.swalisoft.payer.ocr.presentation

import com.swalisoft.payer.note.domain.Category
import com.swalisoft.payer.note.domain.Note

data class OcrState(
  val stage: OcrStage = OcrStage.Idle,
  val isLoading: Boolean = false,
  var note: Note? = null,
  val categories: List<Category> = emptyList(),
)

sealed class OcrStage {
  data object Idle : OcrStage()
  data class Preview(val image: ByteArray): OcrStage()
  data class Error(val message: String) : OcrStage()
}
