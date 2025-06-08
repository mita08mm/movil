package com.swalisoft.payer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.swalisoft.payer.app.AppColorSchema
import com.swalisoft.payer.app.AppTypography
import com.swalisoft.payer.auth.login.presentation.screen.LandingAuthScreen
import com.swalisoft.payer.auth.login.presentation.screen.LoginScreen
import com.swalisoft.payer.auth.login.presentation.screen.RegisterScreen
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.common.getStorageManager
import com.swalisoft.payer.home.presentation.screen.HomeScreen
import com.swalisoft.payer.todo.presentation.screen.ToDoScreen
import com.swalisoft.payer.note.presentation.screen.AddNotesScreen
import com.swalisoft.payer.note.presentation.screen.CategoryNotesScreen
import com.swalisoft.payer.note.presentation.screen.ListNotesScreen
import com.swalisoft.payer.note.presentation.screen.SingleNoteScreen
import com.swalisoft.payer.note.presentation.screen.SubjectScreen
import com.swalisoft.payer.notification.presentation.screen.NotificationScreen
import com.swalisoft.payer.ocr.presentation.screen.OcrScreen
import com.swalisoft.payer.profile.presentation.screen.ProfileScreen
import com.swalisoft.payer.profile.presentation.screen.EditProfileScreen
import com.swalisoft.payer.profile.presentation.screen.ChangePasswordScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
  MaterialTheme(
    typography = AppTypography(),
    colorScheme = AppColorSchema.darkColors,
  ) {
    val navController = rememberNavController()
    val storage = getStorageManager()

    NavHost(
      navController = navController,
      startDestination = if (storage.getAuthToken() == null) Route.LandingAuth else Route.Home,
    ) {

      composable<Route.Login> {
        LoginScreen(
          navigator = navController
        )
      }

      composable<Route.Register> {
        RegisterScreen(
          navigator = navController
        )
      }

      composable<Route.Home> {
        HomeScreen(
          navigator = navController
        )
      }
      composable<Route.LandingAuth> {
        LandingAuthScreen(
          navigator = navController
        )
      }
      composable<Route.ListNotes> {
        ListNotesScreen(
          navigator = navController
        )
      }
      composable<Route.ToDo> {
        ToDoScreen(
          navigator = navController
        )
      }
      composable<Route.Notification> {
        NotificationScreen(
          navigator = navController
        )
      }
      composable<Route.Subject> { backStackEntry ->
        val categoryId = backStackEntry.toRoute<Route.Subject>().categoryId
        SubjectScreen(
          categoryId = categoryId,
          navigator = navController
        )
      }

      composable<Route.Profile> {
        ProfileScreen(
          navigator = navController,
          onEditProfile = { navController.navigate("edit_profile") },
          onChangePassword = { navController.navigate("change_password") }
        )
      }

      composable("edit_profile") {
        EditProfileScreen(navigator = navController)
      }

      composable("change_password") {
        ChangePasswordScreen(navigator = navController)
      }

      composable<Route.AddNotes> { backStackEntry ->
        val categoryId = backStackEntry.toRoute<Route.AddNotes>().categoryId
        AddNotesScreen(
          categoryId = categoryId,
          navigator = navController
        )
      }

      composable<Route.SingleNote> {
        SingleNoteScreen(
          navigator = navController
        )
      }

      composable<Route.OcrScan> {
        OcrScreen(
          navigator = navController
        )
      }

      composable<Route.CategoryNote> {
        CategoryNotesScreen(
          navigator = navController
        )
      }
    }
  }
}
