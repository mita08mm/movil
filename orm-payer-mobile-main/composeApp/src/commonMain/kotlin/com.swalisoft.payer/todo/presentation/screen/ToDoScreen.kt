package com.swalisoft.payer.todo.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.AppColorSchema
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.todo.domain.ToDoModel

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.graphics.Brush

import androidx.compose.foundation.border

import com.swalisoft.payer.todo.domain.Status

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert

import com.swalisoft.payer.home.presentation.components.DateTimePicker
import com.swalisoft.payer.home.presentation.components.getTomorrowDateTime

import kotlinx.datetime.Instant
import kotlinx.datetime.Clock

import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.home.presentation.components.DetailsTaskDialog
import com.swalisoft.payer.home.presentation.components.EditTaskDialog
import org.koin.compose.viewmodel.koinViewModel
import com.swalisoft.payer.component.todo.presentation.ToDoViewModel
import com.swalisoft.payer.core.composable.GradientButtonCircle
import com.swalisoft.payer.home.presentation.components.CreateTaskDialog
import com.swalisoft.payer.home.presentation.components.HomeActionButtons

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.input.pointer.pointerInput

fun LocalDateTime.toIsoLocalString(): String {
    return "${year}-${monthNumber.toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}" +
            "T${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:${second.toString().padStart(2, '0')}Z"
}

@Composable
fun ToDoScreen(navigator: NavHostController) {
    val viewModel = koinViewModel<ToDoViewModel>()
    //val tasks = viewModel.tasks
    val tasks by viewModel.tasks.collectAsState() // Collect the StateFlow

    println("Viendo si las tareas se cargan del back")
    tasks.forEach { task -> println(task.titulo) }

    var showEditDialog by remember { mutableStateOf(false) }
    var editedTask by remember { mutableStateOf<ToDoModel?>(null) }

    if (showEditDialog && editedTask != null) {
        EditTaskDialog(
            task = editedTask!!,
            onDismiss = { showEditDialog = false },
            onUpdateTask = { title, description, dueDate ->
                viewModel.updateTask(
                    editedTask!!.copy(
                        titulo = title,
                        descripcion = description,
                        fechaFinalizacion = dueDate.toString()
                    )
                )
                showEditDialog = false
            }
        )
    }

    var showDetailDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<ToDoModel?>(null) }

    if (showDetailDialog && selectedTask != null) {
        DetailsTaskDialog(
            task = selectedTask!!,
            onDismiss = { showDetailDialog = false }
        )
    }

    var showTaskDialog by remember { mutableStateOf(false) }
    //var newTask by remember { mutableStateOf<ToDoModel?>(null) }

    if (showTaskDialog) {
        CreateTaskDialog(
            onDismiss = { showTaskDialog = false },
            onCreateTask = { title, description, due_date ->
                println("Creating Task: $title, desription: $description")
                val newerTask = ToDoModel("", title, description, Status.PENDING, "", due_date.toIsoLocalString())
                viewModel.createTask(newerTask)
            }
        )
    }

    MainAppLayout(navigator = navigator, title = "Lista de Tareas") {
        Box(modifier = Modifier.fillMaxSize()) {
            TareaTabNavigation(
                tasks = tasks,
                taskStates = tasks.map { mutableStateOf(it.status) },
                onEditTask = { task ->
                    editedTask = task
                    showEditDialog = true
                },
                onDeleteTask = { task ->
                    viewModel.deleteTask(task.id)
                },
                onDetailTask = { task ->
                    selectedTask = task
                    showDetailDialog = true
                },
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                GradientButtonCircle(
                    icon = Icons.Default.Add,
                    onClick = { showTaskDialog = true },
                    modifier = Modifier
                        .size(56.dp) // Tamaño adecuado para el botón
                )
            }
        }
    }

}

@Composable
fun TaskCard(
    task: ToDoModel,
    statusState: MutableState<Status>,
    onEditTask: (ToDoModel) -> Unit,
    onDeleteTask: (ToDoModel) -> Unit,
    onDetailTask: (ToDoModel) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showMenu = true },
                    onTap = { onDetailTask(task) }
                )
            },
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicText(
                    text = task.titulo,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Checkbox (existente)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .then(
                                when (statusState.value) {
                                    Status.COMPLETED -> Modifier.background(
                                        AppColorSchema.PrimaryGradient,
                                        shape = CircleShape
                                    )
                                    Status.PENDING -> Modifier.border(
                                        width = 2.dp,
                                        color = Color.White,
                                        shape = CircleShape
                                    )
                                    Status.ARCHIVE -> Modifier.background(
                                        Color.Gray,
                                        shape = CircleShape
                                    )
                                }
                            )
                    ) {
                        Checkbox(
                            checked = statusState.value == Status.COMPLETED,
                            onCheckedChange = {
                                statusState.value =
                                    if (it) Status.COMPLETED else Status.PENDING
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Transparent,
                                uncheckedColor = Color.Transparent,
                                checkmarkColor = Color.Black
                            )
                        )
                    }

                    // Menú de opciones
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Ver Detalles") },
                            onClick = {
                                showMenu = false
                                onDetailTask(task)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Editar") },
                            onClick = {
                                showMenu = false
                                onEditTask(task)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Eliminar") },
                            onClick = {
                                showMenu = false
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }

            Text(
                text = task.descripcion,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar tarea") },
            text = { Text("¿Estás seguro de que quieres eliminar esta tarea?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteTask(task)
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }
}

enum class TareaTab(val label: String, val route: String) {
    HOY("Hoy", "hoy"),
    PENDIENTES("Pendientes", "pendientes"),
    TODOS("Todos", "todos")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaTabNavigation(
    tasks: List<ToDoModel>,
    taskStates: List<MutableState<Status>>,
    onEditTask: (ToDoModel) -> Unit,
    onDeleteTask: (ToDoModel) -> Unit,
    onDetailTask: (ToDoModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(TareaTab.HOY.ordinal) }
    val tabs = TareaTab.entries

    Scaffold() {
        Column(modifier = Modifier.fillMaxSize()) {
            PrimaryTabRow(selectedTabIndex = selectedTabIndex, containerColor = MaterialTheme.colorScheme.background){
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            navController.navigate(tab.route)
                        },
                        text = {
                            BasicText(
                                text = tab.label,
                                style = TextStyle(
                                    brush = AppColorSchema.PrimaryGradient,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    )
                }
            }

            // Contenido según la pestaña
            NavHost(
                navController = navController,
                startDestination = TareaTab.HOY.route,
                modifier = Modifier.weight(1f) // Hace que el contenido ocupe el resto del espacio
            ) {
                composable(TareaTab.HOY.route) {
                    val hoy = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault())
                        .date
                    val tasksDeHoy = tasks.zip(taskStates).filter { (task, _) ->
                        try {
                            val fecha = Instant.parse(task.fechaFinalizacion)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                                .date
                            fecha == hoy
                        } catch (e: Exception) {
                            false
                        }
                    }
                    VistaTareasHoy(
                        taskPairs = tasksDeHoy,
                        onEditTask = onEditTask,
                        onDeleteTask = onDeleteTask,
                        onDetailTask = onDetailTask
                    )
                }

                composable(TareaTab.PENDIENTES.route) {
                    val tasksPendientes = tasks.zip(taskStates)
                        .filter { (_, statusState) -> statusState.value == Status.PENDING }
                    VistaTareasPendientes(
                        taskPairs = tasksPendientes,
                        onEditTask = onEditTask,
                        onDeleteTask = onDeleteTask,
                        onDetailTask = onDetailTask
                    )
                }

                composable(TareaTab.TODOS.route) {
                    VistaTodasLasTareas(
                        tasks = tasks,
                        taskStates = taskStates,
                        onEditTask = onEditTask,
                        onDeleteTask = onDeleteTask,
                        onDetailTask = onDetailTask
                    )
                }
            }
        }
    }
}


@Composable
fun VistaTareasHoy(
    taskPairs: List<Pair<ToDoModel, MutableState<Status>>>,
    onEditTask: (ToDoModel) -> Unit,
    onDeleteTask: (ToDoModel) -> Unit,
    onDetailTask: (ToDoModel) -> Unit
) {
    if (taskPairs.isNotEmpty()) {
        val (pendingOrArchive, completed) = taskPairs.partition {
            it.second.value != Status.COMPLETED
        }

        val sortByFechaFin = { pair: Pair<ToDoModel, MutableState<Status>> ->
            try {
                Instant.parse(pair.first.fechaFinalizacion)
            } catch (e: Exception) {
                Instant.fromEpochMilliseconds(0)
            }
        }

        LazyColumn {
            items(pendingOrArchive.sortedBy(sortByFechaFin)) { (task, statusState) ->
                TaskCard(
                    task = task,
                    statusState = statusState,
                    onEditTask = onEditTask,
                    onDeleteTask = onDeleteTask,
                    onDetailTask = onDetailTask
                )
            }

            if (completed.isNotEmpty()) {
                item {
                    Text(
                        text = "Tareas Completadas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
                items(completed.sortedBy(sortByFechaFin)) { (task, statusState) ->
                    TaskCard(
                        task = task,
                        statusState = statusState,
                        onEditTask = onEditTask,
                        onDeleteTask = onDeleteTask,
                        onDetailTask = onDetailTask
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay tareas para hoy",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun VistaTareasPendientes(
    taskPairs: List<Pair<ToDoModel, MutableState<Status>>>,
    onEditTask: (ToDoModel) -> Unit,
    onDeleteTask: (ToDoModel) -> Unit,
    onDetailTask: (ToDoModel) -> Unit,
) {
    if (taskPairs.isNotEmpty()) {
        val (pendingOrArchive, completed) = taskPairs.partition {
            it.second.value != Status.COMPLETED
        }

        val sortByFechaFin = { pair: Pair<ToDoModel, MutableState<Status>> ->
            try {
                Instant.parse(pair.first.fechaFinalizacion)
            } catch (e: Exception) {
                Instant.fromEpochMilliseconds(0)
            }
        }

        LazyColumn {
            items(pendingOrArchive.sortedBy(sortByFechaFin)) { (task, statusState) ->
                TaskCard(
                    task = task,
                    statusState = statusState,
                    onEditTask = onEditTask,
                    onDeleteTask = onDeleteTask,
                    onDetailTask = onDetailTask
                )
            }

            if (completed.isNotEmpty()) {
                item {
                    Text(
                        text = "Tareas Completadas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
                items(completed.sortedBy(sortByFechaFin)) { (task, statusState) ->
                    TaskCard(
                        task = task,
                        statusState = statusState,
                        onEditTask = onEditTask,
                        onDeleteTask = onDeleteTask,
                        onDetailTask = onDetailTask
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay tareas Pendientes",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun VistaTodasLasTareas(
    tasks: List<ToDoModel>,
    taskStates: List<MutableState<Status>>,
    onEditTask: (ToDoModel) -> Unit,
    onDeleteTask: (ToDoModel) -> Unit,
    onDetailTask: (ToDoModel) -> Unit,
) {
    if(tasks.isNotEmpty()){
        val (pendingOrArchive, completed) = tasks.zip(taskStates).partition {
            it.second.value != Status.COMPLETED
        }

        val sortByFechaFin = { pair: Pair<ToDoModel, MutableState<Status>> ->
            try {
                Instant.parse(pair.first.fechaFinalizacion)
            } catch (e: Exception) {
                Instant.fromEpochMilliseconds(0)
            }
        }

        LazyColumn {
            items(pendingOrArchive.sortedBy(sortByFechaFin)) { (task, statusState) ->
                TaskCard(
                    task = task,
                    statusState = statusState,
                    onEditTask = onEditTask,
                    onDeleteTask = onDeleteTask,
                    onDetailTask = onDetailTask
                )
            }

            if (completed.isNotEmpty()) {
                item {
                    Text(
                        text = "Tareas Completadas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
                items(completed.sortedBy(sortByFechaFin)) { (task, statusState) ->
                    TaskCard(
                        task = task,
                        statusState = statusState,
                        onEditTask = onEditTask,
                        onDeleteTask = onDeleteTask,
                        onDetailTask = onDetailTask
                    )
                }
            }
        }
    }else{
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
