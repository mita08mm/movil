package com.swalisoft.payer.note.domain

data class Note(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val creationDate: String,
    val categoryId: String,
    val category: Category?
)