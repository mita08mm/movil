package com.swalisoft.payer.notification.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.swalisoft.payer.app.MainAppLayout
import com.swalisoft.payer.notification.presentation.NotificationsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotificationScreen(navigator: NavHostController) {
    val viewModel = koinViewModel<NotificationsViewModel>()
    val notifications = viewModel.notifications

    MainAppLayout(
        navigator = navigator,
        title = "Notificaciones"
    ) {
        Column {
            notifications.forEach { notification ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color(0xFF232428)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = notification.body,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = notification.createdAt,
                            color = Color(0xFF888888),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}