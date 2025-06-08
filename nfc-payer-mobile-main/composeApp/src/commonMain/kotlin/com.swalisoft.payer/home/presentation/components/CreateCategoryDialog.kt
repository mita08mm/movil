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
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import androidx.compose.foundation.layout.width



data class CategoryItem(
    val id: String,
    val name: String,
    val parentId: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
fun CreateCategoryDialog(
    onDismiss: () -> Unit,
    onCreateCategory: (name: String, parentCategory: String?) -> Unit,
    existingCategories: List<CategoryItem> = listOf(
        CategoryItem("1", "Trabajo"),
        CategoryItem("2", "Personal"),
        CategoryItem("3", "Proyectos"),
        CategoryItem("4", "Finanzas"),
        CategoryItem("5", "Compras", "1"),
        CategoryItem("6", "Reuniones", "1")
    ) // Categorías de ejemplo, en producción vendrían del ViewModel
) {
    var categoryName by remember { mutableStateOf("") }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedParentCategory by remember { mutableStateOf<CategoryItem?>(null) }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val density = LocalDensity.current

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
                    text = "Nueva Categoría",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = categoryName,
                    onValueChange = { categoryName = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = categoryName.isEmpty(),
                    supportingText = { 
                        if (categoryName.isEmpty()) {
                            Text("El nombre es obligatorio", color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Desplegable de categoría padre
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Categoría padre (opcional)", 
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    OutlinedTextField(
                        value = selectedParentCategory?.name ?: "Ninguna",
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Seleccionar categoría") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Seleccionar",
                                modifier = Modifier.clickable { isDropdownExpanded = true }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size.toSize()
                            }
                            .clickable { isDropdownExpanded = true }
                    )
                    
                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.width(with(density) { textFieldSize.width.toDp() })
                    ) {
                        // Opción "Ninguna"
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Ninguna",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            onClick = {
                                selectedParentCategory = null
                                isDropdownExpanded = false
                            }
                        )
                        
                        // Lista de categorías existentes
                        existingCategories
                            .filter { it.parentId == null } // Solo mostrar categorías de primer nivel
                            .forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category.name) },
                                    onClick = {
                                        selectedParentCategory = category
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                    }
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
                        Text("Cancelar")
                    }
                    
                    Button(
                        onClick = { 
                            if (categoryName.isNotEmpty()) {
                                onCreateCategory(categoryName, selectedParentCategory?.id)
                                onDismiss()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = categoryName.isNotEmpty()
                    ) {
                        Text("Crear")
                    }
                }
            }
        }
    }
}