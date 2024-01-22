package com.example.blanche.ui.products

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.blanche.R
import com.example.blanche.ui.themes.BlancheTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProduct(
    productName: String,
    productDescription: String,
    productPrice: Double,
    productQuantity: Int,
    onProductNameChanged: (String) -> Unit,
    onProductDescriptionChanged: (String) -> Unit,
    onProductPriceChanged: (Double) -> Unit,
    onProductQuantityChanged: (Int) -> Unit,
    onProductSaved: () -> Unit,
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
                    value = productName,
                    onValueChange = onProductNameChanged,
                    label = { Text("Naam product") },
                )
                Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = productDescription,
                    onValueChange = onProductDescriptionChanged,
                    label = { Text("Beschrijving product") },
                )
                Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = productPrice.toString(),
                    onValueChange = { onProductPriceChanged(it.toDouble()) },
                    label = { Text("Huurprijs") },
                )
                Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = productQuantity.toString(),
                    onValueChange = { onProductQuantityChanged(it.toInt()) },
                    label = { Text("Aantal in stock") },
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
                        text = stringResource(R.string.annuleer),
                        color = Color.Black
                    )
                }
                Spacer(Modifier.width(dimensionResource(id = R.dimen.smallSpacer)))
                TextButton(onClick = onProductSaved) {
                    Text(
                        text = stringResource(R.string.bevestig),
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
        EditProduct("Glazen", "Pils glazen 33cl", 0.2, 100,{},
            {}, {},{}, {}, { /* on dismiss */ })
    }
}