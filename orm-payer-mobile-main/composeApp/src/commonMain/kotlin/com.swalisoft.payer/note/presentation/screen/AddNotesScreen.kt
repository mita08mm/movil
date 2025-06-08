package com.swalisoft.payer.note.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.note.presentation.NoteViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddNotesScreen(
    navigator: NavHostController,
    categoryId: String,
    viewModel: NoteViewModel = koinViewModel()
){
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    DisposableEffect(Unit) {
        onDispose {
            if (title.isNotBlank() || content.isNotBlank()) {
                viewModel.createNote(
                    title = title,
                    content = content,
                    categoryId = categoryId
                )
            }
        }
    }

    MainAppLayout(
        navigator = navigator,
        title = "Nueva Nota",
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            BasicTextField(
                value = title,
                onValueChange = { title = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = MaterialTheme.typography.headlineMedium.lineHeight,
                    fontWeight = FontWeight.Bold
                ),
                decorationBox = { innerTextField ->
                    if (title.isEmpty()) {
                        Text(
                            text = "Añadir Título",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = MaterialTheme.typography.headlineMedium.lineHeight,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            BasicTextField(
                value = content,
                onValueChange = { content = it },
                textStyle = TextStyle(
                    color = Color.LightGray,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold
                ),
                decorationBox = { innerTextField ->
                    if (content.isEmpty()) {
                        Text(
                            text = "Añadir contenido",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    innerTextField()
                },
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }

    }
}