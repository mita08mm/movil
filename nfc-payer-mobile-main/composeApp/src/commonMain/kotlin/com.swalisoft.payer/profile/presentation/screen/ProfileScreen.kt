package com.swalisoft.payer.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.swalisoft.payer.home.presentation.HomeViewModel
import com.swalisoft.payer.profile.presentation.ProfileViewModel
import com.swalisoft.payer.profile.presentation.ProfileUiState
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.LaunchedEffect
import com.swalisoft.payer.app.MainAppLayout

@Composable
fun ProfileScreen(
    navigator: NavHostController,
    userName: String = "User",
    userEmail: String = "user@email.com",
    notificationsEnabled: Boolean = true,
    onEditProfile: () -> Unit = {},
    onChangePassword: () -> Unit = {},
    onLogout: () -> Unit = {
        navigator.navigate(com.swalisoft.payer.common.Route.Login) {
            popUpTo(0)
        }
    },
    onDeleteAccount: () -> Unit = {},
    onToggleNotifications: (Boolean) -> Unit = {},
    onNotifications: () -> Unit = {
        navigator.navigate(com.swalisoft.payer.common.Route.Notification)
    }
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }
    MainAppLayout(
        navigator = navigator,
        title = "Perfil",
        navigationIcon = {
            IconButton(onClick = { navigator.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {},
        floatingActions = {}
    ) {
        ProfileScreenContent(
            userName = userName,
            userEmail = userEmail,
            notificationsEnabled = notificationsEnabled,
            onEditProfile = onEditProfile,
            onChangePassword = onChangePassword,
            onLogout = onLogout,
            onDeleteAccount = onDeleteAccount,
            onToggleNotifications = onToggleNotifications,
            onNotifications = onNotifications
        )
    }
}

@Composable
fun ProfileScreenContent(
    userName: String,
    userEmail: String,
    notificationsEnabled: Boolean,
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    onToggleNotifications: (Boolean) -> Unit,
    onNotifications: () -> Unit
) {
    var allowNotifications by remember { mutableStateOf(notificationsEnabled) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF232428))
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Perfil",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // Avatar
        Box(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color(0xFFFFD966)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Avatar",
                tint = Color(0xFF5A5A5A),
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = userName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = userEmail,
            fontSize = 14.sp,
            color = Color(0xFFB0B0B0),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Divider(color = Color(0xFF444444), thickness = 1.dp)
        ProfileOption(
            icon = Icons.Default.Edit,
            text = "Editar Perfil",
            onClick = onEditProfile
        )
        ProfileOption(
            icon = Icons.Default.Lock,
            text = "Cambiar Contraseña",
            onClick = onChangePassword
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Notificaciones",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = allowNotifications,
                onCheckedChange = {
                    allowNotifications = it
                    onToggleNotifications(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF3ECFFF)
                )
            )
        }
        ProfileOption(
            icon = Icons.Default.Delete,
            text = "Eliminar Cuenta",
            onClick = onDeleteAccount,
            textColor = Color.Red
        )
        ProfileOption(
            icon = Icons.Default.ExitToApp,
            text = "Cerrar Sesión",
            onClick = onLogout,
            textColor = Color(0xFF3ECFFF)
        )
    }
}

@Composable
fun ProfileOption(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = textColor
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
} 