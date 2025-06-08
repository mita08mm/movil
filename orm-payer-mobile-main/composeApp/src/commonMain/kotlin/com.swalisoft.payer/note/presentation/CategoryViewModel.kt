package com.swalisoft.payer.note.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.common.exception.HttpException
import com.swalisoft.payer.note.domain.CategoryNoteRepository
import com.swalisoft.payer.note.presentation.screen.CategoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.swalisoft.payer.note.data.dto.CreateCategoryDto
import com.swalisoft.payer.note.domain.Category

class CategoryViewModel(
    private val repository: CategoryNoteRepository,
): ViewModel() {
    private val _categoryState = MutableStateFlow<Category?>(null)
    val categoryState: StateFlow<Category?> = _categoryState
    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> = _state.stateIn(
        viewModelScope,
        kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    init {
        loadCategories()
    }
    private fun loadCategories() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        try {
            val categories = repository.getCategories()
            _state.update {
                it.copy(
                    isLoading = false,
                    categories = categories,
                    errorMessage = null
                )
            }
        } catch (e: HttpException) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error desconocido"
                )
            }
        }
    }
    fun createCategory (
        name:String,
        description: String? = null,
        parentId: String? = null
    )= viewModelScope.launch {
        if (name.isBlank()) {
            _state.update { it.copy(errorMessage = "El nombre es obligatorio") }
            return@launch
        }
        _state.update { it.copy(isLoading = false, errorMessage = null) }
        try {
            repository.createCategory(
                CreateCategoryDto(
                    name = name.trim(),
                    description = description?.trim(),
                    parentCategoryId = parentId,
                )
            )
            if (parentId != null) {
                getCategory(parentId)
            } else {
                loadCategories()
            }
            _state.update { it.copy(isLoading = false) }
        } catch (e: HttpException) {
            _state.update {
                it.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error al crear categoría"
                )
            }
        }
    }

     fun getCategory(categoryId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            try {
                val category = repository.getCategoryById(categoryId)
                println("Category recibida: $category")
                _categoryState.value = category
                _state.update {
                    it.copy(isLoading = true)
                }
            } catch (e: Exception) {
                println("Error al obtener categoría: ${e.message}")
                _state.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Failed to load category")
                }
            }
        }
    }
}