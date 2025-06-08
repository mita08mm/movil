package com.swalisoft.payer.note.domain

data class Category(
    val id: String,
    val name: String,
    val description: String?,
    val parentCategoryId: String?,
    val childCategories: List<Category>,
    val notes: List<Note>  = emptyList()
)