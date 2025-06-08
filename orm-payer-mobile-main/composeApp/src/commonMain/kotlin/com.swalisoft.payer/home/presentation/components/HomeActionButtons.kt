package com.swalisoft.payer.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.swalisoft.payer.core.composable.GradientButtonCircle

@Composable
fun HomeActionButtons(
    onAddClick: () -> Unit,
    onOcrClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        // Add button for bottom sheet
        GradientButtonCircle(
            icon = Icons.Default.Add,
            onClick = onAddClick
        )
        
        // OCR button (smaller)
        GradientButtonCircle(
            icon = Icons.Default.Person,
            modifier = Modifier.size(44.dp),
            onClick = onOcrClick
        )
    }
} 