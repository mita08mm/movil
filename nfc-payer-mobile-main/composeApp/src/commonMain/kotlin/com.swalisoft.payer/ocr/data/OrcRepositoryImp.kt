package com.swalisoft.payer.ocr.data

import com.swalisoft.payer.note.data.mapper.toDomain
import com.swalisoft.payer.note.domain.Note
import com.swalisoft.payer.ocr.data.mapper.toDto
import com.swalisoft.payer.ocr.data.remote.OrcHttpDataSource
import com.swalisoft.payer.ocr.domain.OcrModel
import com.swalisoft.payer.ocr.domain.OcrRepository

class OrcRepositoryImp(
  private val datasource: OrcHttpDataSource
) : OcrRepository {
  override suspend fun processImage(orc: OcrModel): Note {
//    val note = datasource.getSingleNote("7")
//    println(note)

    val response = datasource.processImage(orc.toDto())

    return response.toDomain()
  }
}