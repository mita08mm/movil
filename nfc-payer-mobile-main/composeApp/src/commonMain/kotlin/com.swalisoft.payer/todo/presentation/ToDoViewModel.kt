/*package com.swalisoft.payer.component.todo.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.todo.domain.ToDoRepository
import com.swalisoft.payer.todo.domain.ToDoModel
import kotlinx.coroutines.launch

class ToDoViewModel (
    private val toDoRepository: ToDoRepository
): ViewModel() {
    private val _tasks = mutableStateListOf<ToDoModel>()
    val tasks: List<ToDoModel> get() = _tasks

    init {
        // Llamamos al repositorio para obtener las notificaciones
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            val result = toDoRepository.getTasks()
            println("RESULTADO GET TASKS: $result") // Log de depuración
            if (result.isSuccess) {
                val lista = result.getOrNull() ?: emptyList()
                println("TAREAS OBTENIDAS: ${lista.size}") // Log de depuración
                _tasks.addAll(lista)
            } else {
                println("ERROR AL OBTENER TAREAS: ${result.exceptionOrNull()?.message}") // Log de depuración
            }
        }
    }

    fun createTask(task: ToDoModel) {
        viewModelScope.launch {
            val result = toDoRepository.createTask(task)
            if (result.isSuccess) {
                refreshTasks()
            } else {
                // Manejar error
            }
        }
    }

    fun updateTask(task: ToDoModel) {
        viewModelScope.launch {
            val result = toDoRepository.updateTask(task.id, task)
            if (result.isSuccess) {
                refreshTasks()
            } else {
                // Manejar error
            }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            val result = toDoRepository.deleteTask(id)
            if (result.isSuccess) {
                refreshTasks()
            } else {
                // Manejar error
            }
        }
    }

    private fun refreshTasks() {
        _tasks.clear()
        getTasks()
    }
}*/

package com.swalisoft.payer.component.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swalisoft.payer.todo.domain.ToDoRepository
import com.swalisoft.payer.todo.domain.ToDoModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<ToDoModel>>(emptyList())
    val tasks: StateFlow<List<ToDoModel>> = _tasks.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            toDoRepository.getTasks()
                .onSuccess { newTasks -> _tasks.value = newTasks }
                .onFailure { /* Manejar error */ }
        }
    }

    fun createTask(task: ToDoModel) {
        viewModelScope.launch {
            toDoRepository.createTask(task)
                .onSuccess { getTasks() }
                .onFailure { /* Manejar error */ }
        }
    }


    fun updateTask(task: ToDoModel) {
        viewModelScope.launch {
            toDoRepository.updateTask(task.id, task)
                .onSuccess { getTasks() }
                .onFailure { /* Manejar error */ }
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            toDoRepository.deleteTask(id)
                .onSuccess { getTasks() }
                .onFailure { /* Manejar error */ }
        }
    }
}