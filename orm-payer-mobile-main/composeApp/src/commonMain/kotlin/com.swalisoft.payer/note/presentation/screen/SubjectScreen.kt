package com.swalisoft.payer.note.presentation.screen


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.core.composable.GradientButtonCircle
import com.swalisoft.payer.note.presentation.CategoryViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.swalisoft.payer.assets.OcrIcons
import com.swalisoft.payer.assets.ocricons.ArrowForward
import com.swalisoft.payer.note.presentation.components.CreateCategory
import com.swalisoft.payer.note.presentation.components.CreateOptionsBottom
import com.swalisoft.payer.note.presentation.components.EmptyState

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    categoryId: String?,
    navigator: NavHostController,
    viewModel: CategoryViewModel = koinViewModel()
) {
    val category by viewModel.categoryState.collectAsState()
    var showCreateCategory by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(categoryId) {
        if (categoryId != null) {
            viewModel.getCategory(categoryId)
        }
    }

    MainAppLayout(
        navigator = navigator,
        title = category?.name ?: "",
        modifier = Modifier.fillMaxHeight().padding(20.dp),
        floatingActions = {
            GradientButtonCircle(
                Icons.Default.Add,
                modifier = Modifier.offset(x = (-8).dp, y = (-20).dp)
            ) {
                if (category?.parentCategoryId == null) {
                    showBottomSheet = true
                } else {
                    category?.id?.let { id ->
                        navigator.navigate(Route.AddNotes(categoryId = id))
                    }
                }
            }
        }
    ) {
        if(!category?.childCategories.isNullOrEmpty()){
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
            ) {
                category?.childCategories?.forEach { childCategory ->
                    ElevatedCard(
                        modifier = Modifier.width(140.dp),
                        onClick = {
                            navigator.navigate(Route.Subject(categoryId = childCategory.id))
                        }
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(16.dp),

                        ) {
                            Text(
                                text = childCategory.name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${childCategory?.notes?.size ?: 0} notas",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        if(!category?.notes.isNullOrEmpty()) {
            Text(
                text = "Notas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                val boxWithConstraintsScope = this
                val space = 20.dp / boxWithConstraintsScope.maxWidth
                // println(boxWithConstraintsScope.maxWidth)
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                ) {
                    category?.notes?.forEach { note ->

                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(0.5f - space / 2),
                            onClick = {
                                navigator.navigate(Route.SingleNote)
                            }
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(16.dp),

                                ) {
                                Text(
                                    text = note.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = note.content.take(100) + if (note.content.length > 100) "..." else "",
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 6
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    if (showBottomSheet) {
        CreateOptionsBottom(
            onDismiss = { showBottomSheet = false },
            sheetState = sheetState,
            onCreateNote = {
                showBottomSheet = false
                category?.id?.let { id ->
                    navigator.navigate(Route.AddNotes(categoryId = id))
                }
            },
            onCreateCategory = {
                showBottomSheet = false
                showCreateCategory = true
            }
        )
    }

    if (showCreateCategory) {
        CreateCategory(
            onDismiss = { showCreateCategory = false },
            onCreateCategory = { name ->
                viewModel.createCategory(
                    name = name,
                    description = null,
                    parentId = categoryId
                )
            }
        )
    }
    if(category?.notes.isNullOrEmpty() && category?.childCategories.isNullOrEmpty()){
        EmptyState(
            icon = OcrIcons.ArrowForward
        )
    }
}