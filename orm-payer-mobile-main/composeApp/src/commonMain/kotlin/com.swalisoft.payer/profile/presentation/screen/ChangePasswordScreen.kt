package com.swalisoft.payer.profile.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun ChangePasswordScreen(
    navigator: NavHostController,
    onChangePassword: (String) -> Unit = {}
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val passwordStrength = getPasswordStrength(newPassword)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF232428))
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navigator.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = "Cambiar Contraseña",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Crea una nueva contraseña segura",
            color = Color(0xFFB0B0B0),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Nueva Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Fuerza: $passwordStrength",
            color = Color(0xFF3ECFFF),
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if (newPassword == confirmPassword && newPassword.isNotBlank()) {
                    onChangePassword(newPassword)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3ECFFF)),
            enabled = newPassword == confirmPassword && newPassword.isNotBlank()
        ) {
            Text("Cambiar Contraseña", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

fun getPasswordStrength(password: String): String {
    return when {
        password.length >= 12 && password.any { it.isDigit() } && password.any { it.isUpperCase() } -> "Fuerte"
        password.length >= 8 -> "Media"
        password.isNotBlank() -> "Débil"
        else -> "---"
    }
} 