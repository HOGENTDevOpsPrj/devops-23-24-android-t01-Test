package com.example.blanche.ui.formulas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.blanche.R
import com.example.blanche.ui.themes.BlancheTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFormula(
    formulaName: String,
    formulaDescription: String,
    pricePerDays: Map<Int, Double>,
    pricePerExtraDay: Double,
    onFormulaNameChanged: (String) -> Unit,
    onFormulaDescriptionChanged: (String) -> Unit,
    onPricePerDayChanged: (Map<Int, Double>) -> Unit,
    onPricePerExtraDayChanged: (Double) -> Unit,
    onFormulaSaved: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onError,
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.mediumPadding))
            ) {
                OutlinedTextField(
                    value = formulaName,
                    onValueChange = onFormulaNameChanged,
                    label = { Text("Naam formule") },
                )
                Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = formulaDescription,
                    onValueChange = onFormulaDescriptionChanged,
                    label = { Text("Beschrijving formule") },
                )
                Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                pricePerDays.forEach { p ->
                    if (p.key == 1) {
                        OutlinedTextField(
                            value = "${p.value}",
                            onValueChange = {
                                val newPricePerdays = pricePerDays.toMutableMap()
                                newPricePerdays.put(p.key, it.toDouble())
                                onPricePerDayChanged(newPricePerdays) },
                            label = { Text("Prijs voor ${p.key} dag") },
                        )
                        Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                    }
                    else {
                        OutlinedTextField(
                            value = "${p.value}",
                            onValueChange = {
                                val newPricePerdays = pricePerDays.toMutableMap()
                                newPricePerdays.put(p.key, it.toDouble())
                                onPricePerDayChanged(newPricePerdays) },
                            label = { Text("Prijs voor ${p.key} dagen") },
                        )
                        Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                    }
                }
                OutlinedTextField(
                    value = pricePerExtraDay.toString(),
                    onValueChange = { onPricePerExtraDayChanged(it.toDouble()) },
                    label = { Text("Prijs per extra dag") },
                )
            }
            Spacer(Modifier.width(dimensionResource(id = R.dimen.smallSpacer)))
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Spacer(Modifier.weight(1F))
                TextButton(onClick = onDismissRequest) {
                    Text(
                        text = "Cancel",
                        color = Color.Black
                    )
                }
                Spacer(Modifier.width(dimensionResource(id = R.dimen.smallSpacer)))
                TextButton(onClick = onFormulaSaved) {
                    Text(
                        text = "Opslaan",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun EditFormulaPreview() {
    BlancheTheme {
        EditFormula("todo", "descr", hashMapOf(0 to 0.0), 0.0, {}, {}, {}, {}, {}, { /* on dismiss */ })
    }
}