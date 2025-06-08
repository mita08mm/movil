package com.swalisoft.payer.core.composable.appBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.swalisoft.payer.app.AppColorSchema
import com.swalisoft.payer.common.Route
import kotlinx.coroutines.launch

data class TopLevelRoute(
  val name: String,
  val route: Route,
  val icon: ImageVector,
)

val topLevelRoutes = listOf(
  TopLevelRoute(name = "Rest", Route.Home, Icons.Default.Home),
)

val optionsRoutes = listOf(
  TopLevelRoute("Test", Route.Languages, Icons.Default.Info),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomAppBar(navigator: NavHostController) {
  val sheetState = rememberModalBottomSheetState(
    skipPartiallyExpanded = true,
  )
  val scope = rememberCoroutineScope()
  var showBottomSheet by remember { mutableStateOf(false) }

  fun navigate(route: Route) {
    navigator.navigate(route) {
      popUpTo(navigator.graph.findStartDestination().id) {
        saveState = true
      }

      launchSingleTop = true
      restoreState = true
    }
  }

  if (showBottomSheet) {
    ModalBottomSheet(
      containerColor = MaterialTheme.colorScheme.surface,
      onDismissRequest = {
        showBottomSheet = false
      },
      properties = ModalBottomSheetProperties(
        shouldDismissOnBackPress = false,
      ),
      sheetState = sheetState,
    ) {
      Column(
        modifier = Modifier.absolutePadding(
          left = 32.dp,
          right = 32.dp,
          bottom = 24.dp,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
      ) {
        Text(
          "Inicio",
          style = MaterialTheme.typography.headlineSmall,
          color = AppColorSchema.SecondaryGrey,
        )

        Column {
          optionsRoutes.forEach { item ->
            HorizontalDivider()
            Row(
              modifier = Modifier
                .clickable(onClick = {
                  scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                      showBottomSheet = false
                    }

                    navigate(item.route)
                  }
                })
                .padding(horizontal = 0.dp, vertical = 16.dp)
            ) {
              Icon(
                imageVector = item.icon,
                contentDescription = item.name,
              )

              Text(
                text = item.name,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
              )
            }
          }
        }
      }
    }
  }

  BottomAppBar(
    containerColor = Color.White,
    contentPadding = PaddingValues(0.dp),
    modifier = Modifier.shadow(elevation = 8.dp),
  ) {
    val currentRoute = navigator.currentBackStackEntry?.destination

    val route = topLevelRoutes.find {
      currentRoute != null && currentRoute.hasRoute(it.route::class)
    }

    topLevelRoutes.forEach { bottomRoute ->
      NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          unselectedIconColor = AppColorSchema.SecondaryGrey,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          unselectedTextColor = AppColorSchema.SecondaryGrey,
          indicatorColor = Color.Transparent,
        ),
        icon = {
          Icon(
            modifier = Modifier.height(24.dp),
            imageVector = bottomRoute.icon,
            contentDescription = bottomRoute.name,
          )
        },
        label = {
          Text(
            text = bottomRoute.name,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        },
        selected = route?.route == bottomRoute.route ||
          (route == null &&  bottomRoute.route === Route.OptionGraph),
        onClick = {
          if (bottomRoute.route == Route.OptionGraph) {
            showBottomSheet = true
          } else {
            navigate(bottomRoute.route)
          }
        }
      )
    }
  }
}
