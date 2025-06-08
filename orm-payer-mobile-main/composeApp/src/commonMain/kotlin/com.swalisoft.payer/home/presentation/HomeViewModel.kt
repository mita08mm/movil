package com.swalisoft.payer.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.swalisoft.payer.AppConfig
import com.swalisoft.payer.todo.data.TaskDto
import com.swalisoft.payer.todo.data.toDomain
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class HomeViewModel(
    private val httpClient: HttpClient,
    private val tokenProvider: suspend () -> String
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            val token = tokenProvider()
            // 1. Stats de tareas de hoy
            val statsResponse = httpClient.get("${AppConfig.API_URL}/tasks/stats/today") {
                headers.append("Authorization", "Bearer $token")
            }
            val stats = statsResponse.body<TaskStats>()

            // 2. Conteo de notas
            val notesResponse = httpClient.get("${AppConfig.API_URL}/notes/count") {
                headers.append("Authorization", "Bearer $token")
            }
            val notesCount = notesResponse.body<NotesCountResponse>().count

            // 3. Tareas pendientes de hoy
            val pendingTasksResponse = httpClient.get("${AppConfig.API_URL}/tasks/today/pending") {
                headers.append("Authorization", "Bearer $token")
            }
            val pendingTasks = pendingTasksResponse.body<List<TaskDto>>().map { it.toDomain() }

            _uiState.update {
                it.copy(
                    stats = stats,
                    notesCount = notesCount,
                    pendingTasksToday = pendingTasks
                )
            }
        }
    }
}

@Serializable
data class NotesCountResponse(val count: Int)