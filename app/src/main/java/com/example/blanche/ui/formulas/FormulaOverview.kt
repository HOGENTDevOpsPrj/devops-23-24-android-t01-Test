package com.example.blanche.ui.formulas

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun FormulaOverview(
    modifier: Modifier = Modifier,
    formulaOverviewViewModel: FormulaOverviewViewModel = viewModel(factory = FormulaOverviewViewModel.Factory),
    isAddingVisible: Boolean = false,
    makeInvisible: () -> Unit = {},
) {
    Log.i("vm inspection", "FormulaOverview composition")
    val formulaOverviewState by formulaOverviewViewModel.uiState.collectAsState()
    val formulaListState by formulaOverviewViewModel.uiListState.collectAsState()

    // use the ApiState
    val formulaApiState = formulaOverviewViewModel.formulaApiState

    Box(modifier = modifier) {
        when (formulaApiState) {
            is FormulaApiState.Loading -> Text("Loading...")
            is FormulaApiState.Error -> Text("Couldn't load...")
            is FormulaApiState.Success -> FormulaListComponent(formulaOverviewState = formulaOverviewState, formulaListState = formulaListState, modifier = modifier)
        }

        if (isAddingVisible) {
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
                    makeInvisible()
                },
                onDismissRequest = { makeInvisible() },
            )
        }
    }
}

@Composable
fun FormulaListComponent(
    modifier: Modifier = Modifier,
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

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun FormulaOverviewPreview() {
    FormulaOverview()
}
