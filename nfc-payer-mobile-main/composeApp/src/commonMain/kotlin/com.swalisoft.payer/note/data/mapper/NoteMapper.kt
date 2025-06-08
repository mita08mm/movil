package com.swalisoft.payer.note.data.mapper

import com.swalisoft.payer.note.data.dto.NoteDto
import com.swalisoft.payer.note.domain.Note

fun NoteDto.toDomain(): Note = Note(
    id = id,
    userId = userId,
    title = title,
    content = content,
    creationDate = creationDate,
    categoryId = categoryId ?: "",
    category?.toDomain()
)