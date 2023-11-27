package com.example.blanche.ui.formulas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun FormulaOverview(
    addingVisible: Boolean,
    onVisibilityChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    formulaOverviewViewModel: FormulaOverviewViewModel = viewModel(factory = FormulaOverviewViewModel.Factory),
) {
    val formulaOverviewState by formulaOverviewViewModel.uiState.collectAsState()
    val formulaListState by formulaOverviewViewModel.uiListState.collectAsState()

    // use the ApiState
    val formulaApiState = formulaOverviewViewModel.formulaApiState

    Box(modifier = modifier) {
        when (formulaApiState) {
            is FormulaApiState.Loading -> Text("Loading...")
            is FormulaApiState.Error -> Text("Couldn't load...")
            is FormulaApiState.Success -> FormulaListComponent(
                formulaOverviewState = formulaOverviewState,
                formulaListState = formulaListState,
            )
        }

        if (addingVisible) {
            CreateFormula(
                formulaName = formulaOverviewState.newFormulaName,
                formulaDescription = formulaOverviewState.newFormulaDescription,
                formulaNrOfDays = formulaOverviewState.newFormulaNrOfDays,
                formulaPrice = formulaOverviewState.newFormulaPrice,
                formulaImageUrl = formulaOverviewState.newFormulaImageUrl,
                onFormulaNameChanged = { formulaOverviewViewModel.setNewFormulaName(it) },
                onFormulaDescriptionChanged = { formulaOverviewViewModel.setNewFormulaDescription(it) },
                onFormulaNrOfDaysChanged = { formulaOverviewViewModel.setNewFormulaNrOfDays(it) },
                onFormulaPriceChanged = { formulaOverviewViewModel.setNewFormulaPrice(it) },
                onFormulaImageUrlChanged = { formulaOverviewViewModel.setNewFormulaImageUrl(it) },

                onFormulaSaved = {
                    formulaOverviewViewModel.addFormula()
                    onVisibilityChanged(false)
                },
                onDismissRequest = { onVisibilityChanged(false) },
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulaOverviewScreen() {
    /*val formulas = listOf(
        Formula(
            name = stringResource(R.string.gewoon_blanche),
            description = stringResource(R.string.formula1_description),
            nrOfDays = 1,
            price = 250.0,
            imageUrl = stringResource(R.string.image_url),
        ),
        Formula(
            name = stringResource(R.string.formula2_name),
            description = stringResource(R.string.formula2_description),
            nrOfDays = 1,
            price = 550.0,
            imageUrl = stringResource(R.string.image_url),
        ),
        Formula(
            name = stringResource(R.string.formula3_name),
            description = stringResource(R.string.formula3_desc),
            nrOfDays = 1,
            price = 750.0,
            imageUrl = stringResource(R.string.image_url),
        ),
    )*/
    val formulaListState by viewModel<FormulaOverviewViewModel>().uiListState.collectAsState()

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
                    .padding(PaddingValues(48.dp)),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                ) {
                    items(formulaListState.formulaList) { formula ->
                        FormulaItem(name = formula.name, price = formula.price)
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
                    Icon(Icons.Default.Home, contentDescription = "")
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

@Preview
@Composable
fun FormulaScreenPreview() {
    FormulaOverviewScreen()
}

@Composable
fun FormulaListComponent(
    formulaOverviewState: FormulaOverviewState,
    formulaListState: FormulaListState,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState) {
        items(formulaListState.formulaList) {
            FormulaItem(name = it.name, price = it.price)
        }
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(formulaOverviewState.scrollActionIdx) {
        if (formulaOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(formulaOverviewState.scrollToItemIndex)
            }
        }
    }
}
