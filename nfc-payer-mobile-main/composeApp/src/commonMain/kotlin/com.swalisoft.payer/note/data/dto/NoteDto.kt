package com.swalisoft.payer.note.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id : String,
    val userId: String,
    val title: String,
    val content: String,
    @SerialName("creationDate") val creationDate: String,
    val categoryId: String?,
    val category: CategoryDto? = null
)
@Serializable
data class CreateNoteDto(
    val title: String,
    val content: String,
    val categoryId: String?
)

