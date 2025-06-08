package com.swalisoft.payer.note.presentation.screen

import com.swalisoft.payer.note.domain.Category
import com.swalisoft.payer.note.domain.Note

data class CategoryState(
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val errorMessage: String? = null,
    val success: Boolean = false,
    val notes: List<Note> = emptyList(),
    val selectedNote: Note? = null,
)
