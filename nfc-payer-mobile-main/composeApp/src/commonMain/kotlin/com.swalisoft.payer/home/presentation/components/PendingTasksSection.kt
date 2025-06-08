package com.swalisoft.payer.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.todo.domain.ToDoModel
import com.swalisoft.payer.todo.presentation.screen.TaskCard
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.style.TextAlign
import com.swalisoft.payer.todo.domain.Status
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun PendingTasksSection(
    navigator: NavHostController,
    tasks: List<ToDoModel>
) {
    var showEditDialog = remember { mutableStateOf(false) }
    var editedTask = remember { mutableStateOf<ToDoModel?>(null) }

    if (showEditDialog.value && editedTask.value != null) {
        EditTaskDialog(
            task = editedTask.value!!,
            onDismiss = { showEditDialog.value = false },
            onUpdateTask = { title, description, dueDate ->
                // Lógica de editar tareas
                showEditDialog.value = false
            }
        )
    }

    var showDetailDialog = remember { mutableStateOf(false) }
    var selectedTask = remember { mutableStateOf<ToDoModel?>(null) }

    if (showDetailDialog.value && selectedTask.value != null) {
//        DetailsTaskDialog(
//            task = selectedTask!!,
//            onDismiss = { showDetailDialog = false }
//        )
    }

    println("Probando si llegaron las tasks...")
    tasks.forEach { task ->
        println("Título: ${task.titulo}, Estado: ${task.status}, Fecha: ${task.fechaFinalizacion}")
    }

    //val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val now = Clock.System.now()
    //val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

    var filteredTasks = tasks
        .filter { task ->
            task.status == Status.PENDING &&
                    try {
                        /*val dueDate = LocalDateTime.parse(task.fechaFinalizacion)
                        dueDate > now*/
                        val dueInstant = Instant.parse(task.fechaFinalizacion)
                        dueInstant > now
                    } catch (e: Exception) {
                        false
                    }
        }
        .sortedBy {
            Instant.parse(it.fechaFinalizacion)
        }
        .take(2)

    println("Probando tasks filtradas...")
    if(filteredTasks.isNotEmpty()){
        filteredTasks.forEach { task ->
            println("Título: ${task.titulo}, Estado: ${task.status}, Fecha: ${task.fechaFinalizacion}")
        }
    }else{
        filteredTasks = listOf(ToDoModel("-1", "Titulo", "Descripocion", Status.PENDING, "2025-06-09T23:59:59.000Z", "2025-06-10T23:59:59.000Z"))
        println("No hay nada XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD")
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tareas pendientes",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            TextButton(onClick = {navigator.navigate(Route.ToDo)}) {
                Text(
                    text = "Ver todo",
                    color = Color(0xFF0088FF)
                )
            }
        }

        val taskStates = remember(tasks) {
            tasks.map { mutableStateOf(it.status) }
        }

        if (tasks.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tasks.zip(taskStates).forEach { (task, statusState) ->
                    TaskCard(
                        task = task,
                        statusState = statusState,
                        onEditTask = { task ->
                            editedTask.value = task
                            showEditDialog.value = true
                        },
                        onDeleteTask = {
                            // Aquí deberías llamar al ViewModel para eliminar la tarea
                        },
                        onDetailTask = { task ->
                            selectedTask.value = task
                            showDetailDialog.value = true
                        },
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay tareas aun",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}