package com.swalisoft.payer.note.data

import com.swalisoft.payer.note.data.dto.CreateCategoryDto
import com.swalisoft.payer.note.data.remote.CategoryHttpDataSource
import com.swalisoft.payer.note.domain.Category
import com.swalisoft.payer.note.domain.CategoryNoteRepository

class CategoryRepositoryImpl(
    private val remote: CategoryHttpDataSource,
) : CategoryNoteRepository {
    override suspend fun getCategories(): List<Category> {
        return remote.getAllCategories()
    }
    override suspend fun createCategory(dto: CreateCategoryDto): Category {
        return remote.createCategory(dto)
    }
    override suspend fun getCategoryById(categoryId: String): Category {
        return remote.getCategoryById(categoryId)
    }
}