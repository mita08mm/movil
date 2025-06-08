package com.swalisoft.payer.note.presentation.screen

import com.swalisoft.payer.common.Route
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.assets.OcrIcons
import com.swalisoft.payer.assets.ocricons.ArrowForward
import com.swalisoft.payer.core.composable.GradientButtonCircle
import com.swalisoft.payer.note.presentation.components.CreateCategory
import com.swalisoft.payer.note.presentation.CategoryViewModel
import com.swalisoft.payer.note.presentation.components.EmptyState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryNotesScreen(
    viewModel: CategoryViewModel = koinViewModel(),
    navigator: NavHostController
){
    val state by viewModel.state.collectAsState()
    var showCreate by remember { mutableStateOf(false) }
    MainAppLayout(
        navigator = navigator,
        title = "Notas",
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        floatingActions = {
            GradientButtonCircle(Icons.Default.Add, modifier = Modifier.offset(x = -8.dp, y = -20.dp)) {
                showCreate = true
            }
        }
    ){
        if (state.categories.isEmpty()) {
            // 👉 Mostrar mensaje de vacío si no hay categorías
            EmptyState(
                icon = OcrIcons.ArrowForward
            )
        } else {
            BoxWithConstraints(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                val boxWithConstraintsScope = this
                val space = 20.dp / boxWithConstraintsScope.maxWidth
                // println(boxWithConstraintsScope.maxWidth)
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                ) {
                     state.categories.forEach { category ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(0.5f - space / 2),
                            onClick = {
                                navigator.navigate(Route.Subject(categoryId = category.id))
                            }
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.padding(16.dp),

                                    ) {
                                    Text(
                                        text = category.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                    Text(text = "${category?.childCategories?.size ?: 0} notas", style = MaterialTheme.typography.bodyMedium)
                                }
                        }
                    }
                }
            }
        }

    }
    if (showCreate) {
        CreateCategory(
            onDismiss = { showCreate = false },
            onCreateCategory = { name ->
                viewModel.createCategory(name, null)
                showCreate = false
            }
        )
    }
}