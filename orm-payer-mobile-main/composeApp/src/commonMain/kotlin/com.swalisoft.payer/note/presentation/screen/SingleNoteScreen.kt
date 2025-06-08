package com.swalisoft.payer.note.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.core.composable.LatexRender
import com.swalisoft.payer.core.composable.MermaidRender

@Composable
fun SingleNoteScreen(navigator: NavHostController) {
  var title by remember { mutableStateOf("") }
  var content by remember { mutableStateOf("") }

  MainAppLayout(
    navigator = navigator,
    title = "Inicio",
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
      Text(
        text = "TExto to input",
        fontSize = MaterialTheme.typography.headlineMedium.lineHeight,
        fontWeight = FontWeight.Bold
      )

      Text("Making the WebView ignore motion events is the wrong way to go about it. What if the WebView needs to hear about these events?", style = MaterialTheme.typography.bodyLarge)

      Text("Making the WebView ignore motion events is the wrong way to go about it. What if the WebView needs to hear about these events?", style = MaterialTheme.typography.bodyLarge)

      LatexRender( content = """
\text{Algebra Formulas} \newline
X = \frac{-b \pm \sqrt{b^2 - 4ac}}{2a} \newline
y = mx + B
      """)

      MermaidRender(content = """
stateDiagram-v2
  [*] --> Initiated

  Initiated --> Initiated: /calculatePrice()
  Initiated --> PreReserved
  Initiated --> [*]

  PreReserved --> Confirmed
  PreReserved --> [*]

  Confirmed --> In_progress
  In_progress --> Done
  Done --> [*]
      """)
    }
  }
}

