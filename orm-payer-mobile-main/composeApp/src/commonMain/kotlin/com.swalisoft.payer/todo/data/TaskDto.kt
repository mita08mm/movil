package com.swalisoft.payer.todo.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class TaskDto(
    val id: String,
    val title: String,
    val description: String? = null,
    @SerialName("creationDate") val creationDate: String,
    @SerialName("dueDate") val dueDate: String? = null,
    val status: String
)

@Serializable
data class TaskCreateDto(
    val title: String,
    val description: String? = null,
    @SerialName("dueDate") val dueDate: String

)