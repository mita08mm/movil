package com.swalisoft.payer.ocr.domain

import com.swalisoft.payer.note.domain.Note

interface OcrRepository {
  suspend fun processImage(orc: OcrModel): Note
}
