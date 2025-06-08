package com.swalisoft.payer.note.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.common.exception.HttpException
import com.swalisoft.payer.note.domain.CategoryNoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import com.swalisoft.payer.note.domain.Note
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.swalisoft.payer.note.data.dto.CreateNoteDto
import com.swalisoft.payer.note.domain.NoteRepository
import com.swalisoft.payer.note.presentation.screen.CategoryState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class NoteViewModel (
    private val repository: NoteRepository,
): ViewModel() {
    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )
    init { loadNotes() }
    private fun loadNotes() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            val notes = repository.getNotes()
            _state.update {
                it.copy(
                    isLoading = false,
                    notes = notes
                ) }
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, errorMessage = e.message) }
        }
    }
    fun createNote(
        title: String,
        content: String,
        categoryId: String
    ) = viewModelScope.launch  {
        if (title.isBlank()) {
            _state.update { it.copy(errorMessage = "El título es obligatorio") }
            return@launch
        }
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        try {
            repository.createNote(
                CreateNoteDto(
                    title.trim(),
                    content.trim(),
                    categoryId
                )
            )
            loadNotes()
        } catch (e: Exception) {
            _state.update { it.copy(isLoading = false, errorMessage = e.message) }
        }
    }

}