package com.swalisoft.payer.home.presentation

import com.swalisoft.payer.todo.domain.ToDoModel
import kotlinx.serialization.Serializable

data class Task(
    val id: Int,
    val title: String,
    val deadline: String,
    val isDone: Boolean = false
)

@Serializable
data class TaskStats(
    val total: Int = 0,
    val completed: Int = 0,
    val pending: Int = 0
)

data class HomeUiState(
    val userName: String = "",
    val stats: TaskStats = TaskStats(),
    val notesCount: Int = 0,
    val pendingTasksToday: List<ToDoModel> = emptyList()
)
