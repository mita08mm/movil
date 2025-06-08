package com.swalisoft.payer.note.data.dto
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    val description: String?,
    val parentCategoryId: String?,
    val childCategories: List<CategoryDto>? = null,
    val notes: List<NoteDto>? = null
)

@Serializable
data class CreateCategoryDto(
    val name: String,
    val description: String? = null,
    val parentCategoryId: String? = null,
    val childCategories: List<CategoryDto>? = emptyList(),
    val notes: List<NoteDto> = emptyList()
)