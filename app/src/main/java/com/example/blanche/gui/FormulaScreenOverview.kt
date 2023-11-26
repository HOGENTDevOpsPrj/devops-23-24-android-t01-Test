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
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blanche.R
import com.example.blanche.model.Formula

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaScreen() {
    val formulas = listOf(
        Formula(
            id = "qsdfq",
            name = stringResource(R.string.gewoon_blanche),
            description = stringResource(R.string.formula1_description),
            nrOfDays = 1,
            price = 250.0,
            imageUrl = stringResource(R.string.image_url),
        ),
        Formula(
            id = "qsdf",
            name = stringResource(R.string.formula2_name),
            description = stringResource(R.string.formula2_description),
            nrOfDays = 1,
            price = 550.0,
            imageUrl = stringResource(R.string.image_url),
        ),
        Formula(
            id = "asdmlk",
            name = stringResource(R.string.formula3_name),
            description = stringResource(R.string.formula3_desc),
            nrOfDays = 1,
            price = 750.0,
            imageUrl = stringResource(R.string.image_url),
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
            Icon(Icons.Default.Edit, contentDescription = "Edit formula")
        }
    }
}

@Preview
@Composable
fun FormulaScreenPreview() {
    FormulaScreen()
}
