package com.swalisoft.payer.note.presentation.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.core.composable.GradientButtonCircle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListNotesScreen(navigator: NavHostController){

    MainAppLayout(
        navigator = navigator,
        title = "Notas Categorizaciones",
        modifier = Modifier.fillMaxWidth().padding(20.dp).horizontalScroll(rememberScrollState()),
        floatingActions = {
            GradientButtonCircle(Icons.Default.Add, modifier = Modifier.offset(x = -8.dp, y = -20.dp)) {
//                navigator.navigate(Route.AddNotes)
            }
        }
    ){
            FlowRow(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                maxItemsInEachRow = 2
            ) {
                ElevatedCard(Modifier.weight(2f)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(16.dp),

                    ) {
                        Text(
                            text = "Study",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "10 notas", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                ElevatedCard(Modifier.weight(2f)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = "UI ui",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "20 notas", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                ElevatedCard(Modifier.weight(2f)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "1 nota", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                ElevatedCard(Modifier.weight(2f)) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Login", style = MaterialTheme.typography.bodyMedium)
                    }
                }
//            Icon(imageVector = Icons.Default.Add, contentDescription = null )
//            GradientButtonCircle(icon = Icons.Default.Add) {}

        }

    }
}