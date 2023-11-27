package com.example.blanche.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheAppBottomBar(goHome: () -> Unit, goToAbout: () -> Unit) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(onClick = goHome) {
                Icon(Icons.Filled.Home, contentDescription = "navigate to home screen")
            }
            IconButton(onClick = goToAbout) {
                Icon(Icons.Filled.Info, contentDescription = "navigate to about page")
            }
        },
    )
}

@Preview
@Composable
fun BlancheAppBottomBarPreview() {
    BlancheAppBottomBar(goHome = {}, goToAbout = {})
}
