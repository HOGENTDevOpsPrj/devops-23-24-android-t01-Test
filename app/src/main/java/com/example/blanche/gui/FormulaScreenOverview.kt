package com.example.blanche.gui
// FormulaScreen.kt

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blanche.data.Formula

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaScreen() {
    val formulas = listOf(
        Formula(
            id = "1",
            name = "Gewoon Blanche",
            description = "Geniet van de essentie van Blanche met onze basis formule. Da's gewoon Blanche",
            nrOfDays = 1,
            price = 250.0,
            imageUrl = "",
        ),
        Formula(
            id = "2",
            name = "Brew Buggy",
            description = "Mobiele bierervaring op zijn best. Geniet van ambachtelijk bier, direct getapt vanuit deze stijlvolle mobiele tapinstallatie.",
            nrOfDays = 1,
            price = 550.0,
            imageUrl = "",
        ),
        Formula(
            id = "3",
            name = "Bier & Bite Bonanza",
            description = "Stap in de wereld van smaak. Huur de foodtruck en voeg een extra dimensie toe met een selectie van bieren naar keuze, perfect samengesteld met een heerlijke hamburger.",
            nrOfDays = 1,
            price = 750.0,
            imageUrl = "",
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formula List") },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            )
        },
        content = {
            Surface(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(PaddingValues(36.dp)),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                ) {
                    items(formulas) { formula ->
                        FormulaItem(formula = formula) {
                            println("Formula clicked: ${formula.name}")
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                IconButton(
                    onClick = { /* Handle home item click */ },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(Icons.Default.Home, contentDescription = null)
                }

                IconButton(
                    onClick = { /* Handle add item click */ },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }

                IconButton(
                    onClick = { /* Handle profile item click */ },
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            }
        },
    )
}

@Composable
fun FormulaItem(formula: Formula, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick() }, // Make the entire card clickable
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column {
                Text(text = formula.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Price: ${formula.price}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Preview
@Composable
fun FormulaScreenPreview() {
    FormulaScreen()
}
