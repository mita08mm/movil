package com.swalisoft.payer.ocr.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.text.font.FontWeight
import com.swalisoft.payer.app.AppColorSchema
import com.swalisoft.payer.core.composable.GradientButtonCircle
import com.swalisoft.payer.note.domain.Category
import com.swalisoft.payer.ocr.presentation.OcrViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OcrPreview(
  image: ByteArray,
  viewModel: OcrViewModel = koinViewModel(),
  onConfirm: (categoryId: String) -> Unit,
) {
  var selectedCategory by remember { mutableStateOf<Category?>(null) }
  var searchQuery by remember { mutableStateOf("") }
  val state = viewModel.state.collectAsState()

  LaunchedEffect(Unit) {
    viewModel.getCategories()
  }

  val categories = state.value.categories
  val filteredCategories = categories.filter { it.name.contains(searchQuery, ignoreCase = true) }

  Column(
    modifier = Modifier.padding(20.dp).fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        shape = MaterialTheme.shapes.large,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.requiredHeight(45.dp).weight(1f),
        placeholder = { Text(
          text = "Buscar...",
          color = AppColorSchema.PlaceholderColor,
          style = MaterialTheme.typography.bodyMedium,
        )},
      )

      GradientButtonCircle(
        icon = Icons.Default.Check,
        modifier = Modifier.size(44.dp),
        enabled = selectedCategory != null,
        onClick = { onConfirm(selectedCategory!!.id) }
      )
    }

    Text(
      text = "Selecciona una categoría:",
      style = MaterialTheme.typography.bodyLarge,
      fontWeight = FontWeight.Bold,
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      items(filteredCategories.size) { i ->
        val category = filteredCategories[i]
        val isSelected = selectedCategory?.id == category.id

        ElevatedCard(
          colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified,
          ),
          modifier = Modifier.align(Alignment.CenterHorizontally),
          onClick = {
            selectedCategory = category
          }
        ) {
          Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
          )
        }
      }
    }

    if (image.isNotEmpty()) {
      Image(
        bitmap = image.decodeToImageBitmap(),
        contentDescription = "Preview",
        modifier = Modifier.fillMaxWidth().clip(MaterialTheme.shapes.medium),
      )
    } else {
      Text(
        text = "Sin imagen que mostrar",
        style = MaterialTheme.typography.bodyLarge
      )
    }
  }
}
