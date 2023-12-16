package com.example.blanche.ui.formulas

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blanche.R
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

    val showEditScreen = formulaOverviewViewModel.showEditFormulaScreen.collectAsState().value

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
                onDismissRequest = { makeInvisible },
            )
        }

        if (showEditScreen) {
            Dialog(onDismissRequest = { }) {
                EditFormula(
                    formulaName = formulaOverviewState.newFormulaName,
                    formulaDescription = formulaOverviewState.newFormulaDescription,
                    pricePerDays = formulaOverviewState.newFormulaPricePerDays,
                    pricePerExtraDay = formulaOverviewState.newFormulaPricePerExtraDay,
                    onFormulaNameChanged = { formulaOverviewViewModel.setNewFormulaName(it) },
                    onFormulaDescriptionChanged = { formulaOverviewViewModel.setNewFormulaDescription(it) },
                    onPricePerDayChanged = { formulaOverviewViewModel.setNewPricePerDays(it) },
                    onPricePerExtraDayChanged = { formulaOverviewViewModel.setNewPricePerExtraDay(it) },
                    onFormulaSaved = {
                        formulaOverviewViewModel.updateFormula()
                        formulaOverviewViewModel.toggleEditFormulaScreen()
                    },
                    onDismissRequest = { formulaOverviewViewModel.toggleEditFormulaScreen() },
                )
            }
        }
    }
}

@Composable
fun FormulaListComponent(
    modifier: Modifier = Modifier,
    formulaOverviewState: FormulaOverviewState,
    formulaListState: FormulaListState,
) {
    val viewModel: FormulaOverviewViewModel = viewModel(factory = FormulaOverviewViewModel.Factory,)
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        items(formulaListState.formulaList) {
            FormulaItem(formula = it, viewModel = viewModel)
            Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
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

/*@Preview(showBackground = true, widthDp = 1000)
@Composable
fun FormulaOverviewPreview() {
    FormulaOverview()
}*/
