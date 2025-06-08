package com.swalisoft.payer.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.swalisoft.payer.common.Route
import com.swalisoft.payer.core.composable.GradientButtonCircle
import com.swalisoft.payer.common.vectors.KeyboardBackspace
import com.swalisoft.payer.common.vectors.Vectors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppLayout(
    navigator: NavHostController,
    title: String,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    modifier: Modifier = Modifier,
    handleBack: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.background,
    floatingActions: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {
        IconButton(
            onClick = {
                navigator.navigate(Route.Notification)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications"
            )
        }
        IconButton(
            onClick = {
                navigator.navigate(Route.Profile)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil"
            )
        }
    },
    navigationIcon: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val defaultBack: () -> Unit = {
        navigator.popBackStack()
    }

    Scaffold(
        containerColor = containerColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                navigationIcon = navigationIcon ?: {
                    if(navigator.previousBackStackEntry != null) {
                        IconButton(
                            onClick = handleBack ?: defaultBack
                        ) {
                            Icon(imageVector = Vectors.KeyboardBackspace, contentDescription = null)
                        }
                    }
                },
                actions = actions
            )
        },
        floatingActionButton = floatingActions,
        content = { innerPadding ->
            Column(
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = verticalArrangement,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .composed { modifier },
                content = content
            )
        }
    )
}
