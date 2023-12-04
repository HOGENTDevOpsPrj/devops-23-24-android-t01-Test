package com.example.blanche.ui.formulas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.blanche.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateFormula(
    formulaName: String,
    formulaDescription: String,
    formulaNrOfDays: Int,
    formulaPrice: Double,
    formulaImageUrl: String,
    onFormulaNameChanged: (String) -> Unit,
    onFormulaDescriptionChanged: (String) -> Unit,
    onFormulaNrOfDaysChanged: (Int) -> Unit,
    onFormulaPriceChanged: (Double) -> Unit,
    onFormulaImageUrlChanged: (String) -> Unit,
    onFormulaSaved: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.mediumPadding)),
            ) {
                OutlinedTextField(
                    value = formulaName,
                    onValueChange = onFormulaNameChanged,
                    label = { Text("formula name") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = formulaDescription,
                    onValueChange = onFormulaDescriptionChanged,
                    label = { Text("description") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = formulaNrOfDays.toString(),
                    onValueChange = { onFormulaNrOfDaysChanged(it.toInt()) },
                    label = { Text("number of days") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = formulaPrice.toString(),
                    onValueChange = { onFormulaPriceChanged(it.toDouble()) },
                    label = { Text("price") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = formulaImageUrl,
                    onValueChange = onFormulaImageUrlChanged,
                    label = { Text("price") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))
                Row {
                    Spacer(Modifier.weight(1F))
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.smallSpacer)))
                    TextButton(onClick = onFormulaSaved) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateTaskPreview() {
    CreateFormula(
        formulaName = "",
        formulaDescription = "",
        formulaNrOfDays = 0,
        formulaPrice = 0.0,
        formulaImageUrl = "",
        onFormulaNameChanged = {},
        onFormulaDescriptionChanged = {},
        onFormulaNrOfDaysChanged = {},
        onFormulaPriceChanged = {},
        onFormulaImageUrlChanged = {},
        onFormulaSaved = {},
        onDismissRequest = {},
    )
}
