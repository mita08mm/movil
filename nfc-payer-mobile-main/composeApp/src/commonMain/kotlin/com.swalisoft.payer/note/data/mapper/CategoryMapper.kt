package com.swalisoft.payer.note.data.mapper

import com.swalisoft.payer.note.domain.Category
import com.swalisoft.payer.note.data.dto.CategoryDto
fun CategoryDto.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        description = description,
        parentCategoryId = parentCategoryId,
        childCategories = childCategories?.map { it.toDomain() } ?: emptyList(),
        notes = notes?.map { it.toDomain() } ?: emptyList()
    )
}
