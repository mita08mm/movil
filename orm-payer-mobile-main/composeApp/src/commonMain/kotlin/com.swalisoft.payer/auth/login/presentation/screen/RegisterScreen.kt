package com.swalisoft.payer.auth.login.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.layout.AuthLayout
import com.swalisoft.payer.auth.login.presentation.LoginViewModel
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.core.composable.GradientButton
import com.swalisoft.payer.core.composable.input.CustomOutlinedField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
  viewModel: LoginViewModel = koinViewModel(),
  navigator: NavHostController,
) {
  val state by viewModel.state.collectAsState()

  LaunchedEffect(state.successLogin) {
    if (state.successLogin) {
      navigator.navigate(Route.Home) {
        popUpTo(id = 0) {
          inclusive = true
        }
      }
    }
  }

  AuthLayout(
    modifier = Modifier
      .padding(20.dp)
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    verticalArrangement = Arrangement.SpaceBetween,
  ) {
    val email = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
      verticalArrangement = Arrangement.spacedBy(24.dp),
      modifier = Modifier.padding(top = 20.dp)
    ) {
      Text(
        text = "¡Comienza una aventura con nosotros! Regístrate",
        style = MaterialTheme.typography.displayMedium,
        color = Color.White,
      )

      CustomOutlinedField(
        value = name.value,
        placeholder = "Nombre",
        label = "Nombre",
      ) { name.value = it }

      CustomOutlinedField(
        value = email.value,
        placeholder = "Correo",
        label = "Correo electrónico",
      ) { email.value = it }

      CustomOutlinedField(
        value = password.value,
        placeholder = "Contraseña",
        label = "Contraseña",
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Password,
          imeAction = ImeAction.Done,
        ),
        visualTransformation = PasswordVisualTransformation(),
      ) { password.value = it }
    }

    Column(
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      if (state.isLoading) {
        Text("Verificando con API...")
      }

      GradientButton(
        text = "Registrars",
        modifier = Modifier.fillMaxWidth(),
      ) {
        viewModel.registerUser(name.value, email.value, password.value)
      }

      TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
          navigator.navigate(Route.Login)
        },
      ) {
        Text(
          text = "¿Ya tienes una cuenta? ingresar",
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
        )
      }
    }
  }
}
