package com.swalisoft.payer.home.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.common.vectors.Group2
import com.swalisoft.payer.common.vectors.Vector1
import com.swalisoft.payer.common.vectors.Vectors
import com.swalisoft.payer.home.presentation.HomeViewModel
import com.swalisoft.payer.home.presentation.components.*
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.setValue
import com.swalisoft.payer.home.presentation.components.CreateCategoryDialog
import com.swalisoft.payer.home.presentation.components.CategoryItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigator: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var showTaskDialog by remember { mutableStateOf(false) }
    var showCategoryDialog by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()

    // Lista de categorías de ejemplo para el diálogo
    val sampleCategories = listOf(
        CategoryItem("1", "Trabajo"),
        CategoryItem("2", "Personal"),
        CategoryItem("3", "Proyectos"),
        CategoryItem("4", "Finanzas"),
        CategoryItem("5", "Compras", "1"),
        CategoryItem("6", "Reuniones", "1")
    )

    // Task creation dialog
    if (showTaskDialog) {
        CreateTaskDialog(
            onDismiss = { showTaskDialog = false },
            onCreateTask = { title, description, due_date ->
                println("Creating Task: $title, desription: $description")
                // Handle category creation logic here
            }
        )

    }

    // Category creation dialog
    if (showCategoryDialog) {
        CreateCategoryDialog(
            onDismiss = { showCategoryDialog = false },
            onCreateCategory = { name, parentCategory ->
                println("Creating category: $name, parent: $parentCategory")
                // Handle category creation logic here
            },
            existingCategories = sampleCategories
        )
    }

    // Create options bottom sheet
    if (showBottomSheet) {
        CreateOptionsBottomSheet(
            onDismiss = { showBottomSheet = false },
            sheetState = bottomSheetState,
            onCreateNote = {
                showBottomSheet = false
//                navigator.navigate(Route.AddNotes)
            },
            onCreateTask = {
                showBottomSheet = false
                showTaskDialog = true
            },
            onCreateCategory = {
                showBottomSheet = false
                showCategoryDialog = true
            }
        )
    }

    MainAppLayout(navigator = navigator, title = "Inicio") {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Contenido scrollable
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ProgressCard(
                    completedTasks = uiState.stats.completed,
                    totalTasks = uiState.stats.total
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        StatCard(
                            modifier = Modifier.fillMaxWidth(),
                            icon = Vectors.Group2,
                            title = "Notas",
                            value = "${uiState.notesCount} notas",
                        ) {
                            navigator.navigate(Route.CategoryNote)
                        }
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        StatCard(
                            modifier = Modifier.fillMaxWidth(),
                            icon = Vectors.Vector1,
                            title = "Lista de tareas",
                            value = "${uiState.stats.total} tasks"
                        ) {
                            navigator.navigate(Route.ToDo)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                PendingTasksSection(
                    navigator = navigator,
                    tasks = uiState.pendingTasksToday
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                HomeActionButtons(
                    onAddClick = { showBottomSheet = true },
                    onOcrClick = { navigator.navigate(Route.OcrScan) }
                )
            }
        }
    }
}