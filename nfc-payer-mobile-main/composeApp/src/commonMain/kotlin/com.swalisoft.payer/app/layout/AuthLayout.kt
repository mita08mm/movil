package com.swalisoft.payer.app.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import com.swalisoft.payer.app.composable.SnackbarHost

@Composable
fun AuthLayout(
  modifier: Modifier = Modifier,
  verticalArrangement: Arrangement.Vertical = Arrangement.Top,
  horizontalAlignment: Alignment.Horizontal = Alignment.Start,
  containerColor: Color = MaterialTheme.colorScheme.background,
  content: @Composable ColumnScope.() -> Unit,
) {

  Scaffold(
    containerColor = containerColor,
    snackbarHost = { SnackbarHost() },
    content = { innerPadding ->
      Column(
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        modifier = Modifier
          .padding(innerPadding)
          .fillMaxSize()
          .composed { modifier },
        content = content,
      )
    },
  )
}
