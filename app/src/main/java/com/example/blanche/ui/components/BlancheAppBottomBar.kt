package com.example.blanche.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheAppBottomBar(goHome: () -> Unit, goToReservations: () -> Unit, goToFormulas: () -> Unit) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.outlineVariant,
        contentColor = MaterialTheme.colorScheme.surface,
        actions = {
            IconButton(onClick = goHome) {
                Icon(Icons.Filled.Home, contentDescription = "navigate to home screen")
            }

            IconButton(onClick = goToReservations) {
                Icon(Icons.Filled.CalendarMonth, contentDescription = "navigate to reservations screen")
            }

            IconButton(onClick = goHome) {
                Icon(Icons.Filled.AddCircleOutline, contentDescription = "navigate to extra material screen")
            }

            IconButton(onClick = goToFormulas) {
                Icon(Icons.Filled.ListAlt, contentDescription = "navigate to extra material screen")
            }
        },
    )
}

@Preview
@Composable
fun BlancheAppBottomBarPreview() {
    BlancheAppBottomBar(goHome = {}, goToReservations = {}, goToFormulas = {})
}
