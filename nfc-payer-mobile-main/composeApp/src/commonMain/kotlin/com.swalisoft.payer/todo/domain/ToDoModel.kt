package com.swalisoft.payer.todo.domain


data class ToDoModel (
    val id: String = "",
    val titulo: String,
    val descripcion: String,
    val status: Status,
    val fechaCreacion: String,
    val fechaFinalizacion: String
)