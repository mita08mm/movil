package com.swalisoft.payer.note.domain

import com.swalisoft.payer.note.data.dto.CreateCategoryDto
import com.swalisoft.payer.note.data.dto.CreateNoteDto

interface CategoryNoteRepository {
    suspend fun getCategories(): List<Category>
    suspend fun createCategory(dto: CreateCategoryDto): Category
    suspend fun getCategoryById(categoryId: String): Category
}