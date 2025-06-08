package com.swalisoft.payer.ocr.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.note.domain.CategoryNoteRepository
import com.swalisoft.payer.ocr.domain.OcrModel
import com.swalisoft.payer.ocr.domain.OcrRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OcrViewModel(
  private val ocrRepository: OcrRepository,
  private val categoryRepository: CategoryNoteRepository,
) : ViewModel() {
  private val _state = MutableStateFlow(OcrState())

  val state = _state
    .stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000L),
    _state.value,
  )

  fun backToCamera() {
    _state.update {
      it.copy(stage = OcrStage.Idle)
    }
  }

  fun preview(imageBytes: ByteArray) {
    _state.update {
      it.copy(
        stage = OcrStage.Preview(imageBytes),
        isLoading = true,
      )
    }
  }

  fun getCategories() = viewModelScope.launch {
    _state.update { it.copy(isLoading = true) }

    try {
      val note = categoryRepository.getCategories()

      _state.update { it.copy(categories = note) }
    } catch (e: Exception) {
      println(e)
      _state.update { it.copy(stage = OcrStage.Error("Error al leer texto")) }
    }

    _state.update { it.copy(isLoading = false) }
  }

  fun processImage(imageBytes: ByteArray, categoryId: String) = viewModelScope.launch {
    _state.update { it.copy(isLoading = true) }

    try {
      val note = ocrRepository.processImage(
        OcrModel(
          image = imageBytes,
          categoryId = categoryId,
          imageName = null,
        )
      )

      _state.value.note = note
    } catch (e: Exception) {
      println(e)
      _state.update { it.copy(stage = OcrStage.Error("Error al leer texto")) }
    }

    _state.update { it.copy(isLoading = false) }
  }
}
