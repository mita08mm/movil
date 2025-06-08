package com.swalisoft.payer.todo.domain

import com.swalisoft.payer.todo.domain.ToDoModel

interface ToDoRepository {
    suspend fun getTasks(): Result<List<ToDoModel>>
    suspend fun createTask(task: ToDoModel): Result<ToDoModel>
    suspend fun updateTask(id: String, task: ToDoModel): Result<ToDoModel>
    suspend fun deleteTask(id: String): Result<Unit>
}