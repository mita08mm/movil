package com.swalisoft.payer.auth.login.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.layout.AuthLayout
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.core.composable.GradientButton
import org.jetbrains.compose.resources.painterResource
import payer.composeapp.generated.resources.Res
import payer.composeapp.generated.resources.mobile_notes_list

@Composable
fun LandingAuthScreen(navigator: NavHostController) {
  AuthLayout(
    modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp).fillMaxHeight(),
    verticalArrangement = Arrangement.Bottom,
  ) {
    Image(
      modifier = Modifier.fillMaxWidth(),
      painter = painterResource(Res.drawable.mobile_notes_list),
      contentScale = ContentScale.FillWidth,
      contentDescription = null,
    )

    Spacer(modifier = Modifier.height(48.dp))

    Column {
      Text(
        text = "Nota",
        style = MaterialTheme.typography.displayMedium,
        color = Color.White,
      )

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Effortlessly organize your daily life and stay on top of tasks",
        style = MaterialTheme.typography.bodyLarge,
      )
    }

    Spacer(modifier = Modifier.height(60.dp))

    GradientButton(
      text = "Siguiente",
      modifier = Modifier.fillMaxWidth(),
    ) {
      navigator.navigate(Route.Login)
    }
  }
}
