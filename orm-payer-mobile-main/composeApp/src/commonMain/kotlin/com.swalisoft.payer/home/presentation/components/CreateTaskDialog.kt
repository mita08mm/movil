package com.swalisoft.payer.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import com.swalisoft.payer.todo.domain.Status
import com.swalisoft.payer.todo.domain.ToDoModel
import kotlinx.datetime.LocalDate
import com.swalisoft.payer.home.presentation.components.DateTimePicker
import com.swalisoft.payer.home.presentation.components.getCurrentDateTime
import com.swalisoft.payer.home.presentation.components.getTomorrowDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class TaskItem(
    val id: String,
    val title: String,
    val description: String,
    val due_date: LocalDateTime
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskDialog(
    onDismiss: () -> Unit,
    onCreateTask: (title: String, description: String, due_date: LocalDateTime) -> Unit,
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf(getTomorrowDateTime()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nueva Tarea",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = taskTitle.isEmpty(),
                    supportingText = {
                        if (taskTitle.isEmpty()) {
                            Text("El título es obligatoria", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = taskDescription.isEmpty(),
                    supportingText = {
                        if (taskDescription.isEmpty()) {
                            Text("La descripción es obligatorio", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                DateTimePicker(
                    selectedDateTime = selectedDateTime,
                    onDateTimeSelected = { newDateTime ->
                        selectedDateTime = newDateTime
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            if (taskTitle.trim().isNotEmpty() && taskDescription.trim().isNotEmpty()) {
                                onCreateTask(taskTitle, taskDescription, selectedDateTime)
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = taskTitle.isNotEmpty() && taskDescription.isNotEmpty()
                    ) {
                        Text("Crear")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskDialog(
    task: ToDoModel,
    onDismiss: () -> Unit,
    onUpdateTask: (title: String, description: String, due_date: LocalDateTime) -> Unit,
) {
    var taskTitle by remember { mutableStateOf(task.titulo) }
    var taskDescription by remember { mutableStateOf(task.descripcion) }
    var selectedDateTime by remember {
        mutableStateOf(Instant.parse(task.fechaFinalizacion).toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Editar Tarea",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = taskTitle.isEmpty(),
                    supportingText = {
                        if (taskTitle.isEmpty()) {
                            Text("El título es obligatoria", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = taskDescription.isEmpty(),
                    supportingText = {
                        if (taskDescription.isEmpty()) {
                            Text("La descripción es obligatorio", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                DateTimePicker(
                    selectedDateTime = selectedDateTime,
                    onDateTimeSelected = { newDateTime ->
                        selectedDateTime = newDateTime
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            if (taskTitle.trim().isNotEmpty() && taskDescription.trim().isNotEmpty()) {
                                onUpdateTask(taskTitle, taskDescription, selectedDateTime)
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = taskTitle.isNotEmpty() && taskDescription.isNotEmpty()
                    ) {
                        Text("Editar")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTaskDialog(
    task: ToDoModel,
    onDismiss: () -> Unit
) {
    var selectedDateTime by remember {
        mutableStateOf(Instant.parse(task.fechaFinalizacion).toLocalDateTime(TimeZone.currentSystemDefault()))
    }
    var selectedCreatedTime by remember {
        mutableStateOf(Instant.parse(task.fechaCreacion).toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Detalles de la Tarea",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Titulo:",
                    fontWeight = FontWeight.Bold
                )
                Text(task.titulo)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Descripcion:",
                    fontWeight = FontWeight.Bold
                )
                Text(task.descripcion)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Fecha de Creacion:",
                    fontWeight = FontWeight.Bold
                )
                Text("${selectedDateTime.dayOfMonth.toString().padStart(2, '0')}/" +
                        "${selectedDateTime.monthNumber.toString().padStart(2, '0')}/" +
                        "${selectedDateTime.year} " +
                        "${selectedDateTime.hour.toString().padStart(2, '0')}:" +
                        "${selectedDateTime.minute.toString().padStart(2, '0')}")

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Fecha de Finalizacion:",
                    fontWeight = FontWeight.Bold
                )
                Text("${selectedCreatedTime.dayOfMonth.toString().padStart(2, '0')}/" +
                        "${selectedCreatedTime.monthNumber.toString().padStart(2, '0')}/" +
                        "${selectedCreatedTime.year} " +
                        "${selectedCreatedTime.hour.toString().padStart(2, '0')}:" +
                        "${selectedCreatedTime.minute.toString().padStart(2, '0')}")

                Spacer(modifier = Modifier.height(24.dp))

                if(task.status == Status.PENDING){
                    Text(
                        text = "Estado de la tarea:",
                        fontWeight = FontWeight.Bold
                    )
                    Text("En proceso...")
                }else if(task.status == Status.COMPLETED){
                    Text(
                        text = "Estado de la tarea:",
                        fontWeight = FontWeight.Bold
                    )
                    Text("Finalizada")
                }else{
                    Text(
                        text = "Estado de la tarea:",
                        fontWeight = FontWeight.Bold
                    )
                    Text("Expirada")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text("Cerrar")
                    }
                }
            }
        }
    }
}