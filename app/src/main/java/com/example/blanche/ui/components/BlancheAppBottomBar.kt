package com.example.blanche.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddToPhotos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlancheAppBottomBar(
    goHome: () -> Unit,
    goToReservations: () -> Unit,
    goToFormulas: () -> Unit,
    goToProduct:() -> Unit)
{
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.outlineVariant,
        contentColor = MaterialTheme.colorScheme.surface,
        actions = {
            Row(
                Modifier.fillMaxWidth().paddingFromBaseline(top = 0.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = goHome) {
                        Icon(Icons.Filled.Home, contentDescription = "navigate to home screen")
                    }
                    Text(
                        text = "Homepage",
                        fontSize = 12.sp
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = goToReservations) {
                        Icon(
                            Icons.Filled.CalendarMonth,
                            contentDescription = "navigate to reservations screen"
                        )
                    }
                    Text(
                        text = "Reservaties",
                        fontSize = 12.sp
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = goToProduct) {
                        Icon(
                            Icons.Filled.AddToPhotos,
                            contentDescription = "navigate to extra material screen"
                        )
                    }
                    Text(
                        text = "Materiaal",
                        fontSize = 12.sp
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = goToFormulas) {
                        Icon(
                            Icons.Filled.ListAlt,
                            contentDescription = "navigate to extra material screen"
                        )
                    }
                    Text(
                        text = "Formules",
                        fontSize = 12.sp
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun BlancheAppBottomBarPreview() {
    BlancheAppBottomBar(goHome = {}, goToReservations = {}, goToFormulas = {}, goToProduct = {})
}
