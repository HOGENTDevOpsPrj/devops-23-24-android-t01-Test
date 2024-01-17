package com.example.blanche.ui.products

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.blanche.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProduct(
    productName: String,
    productDescription: String,
    productQuantity: Int,
    productPrice: Double,
    productImageUrl: String,
    onProductNameChanged: (String) -> Unit,
    onProductDescriptionChanged: (String) -> Unit,
    onProductQuantityChanged: (Int) -> Unit,
    onProductPriceChanged: (Double) -> Unit,
    onProductImageUrlChanged: (String) -> Unit,
    onProductSaved: () -> Unit,
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
                    value = productName,
                    onValueChange = onProductNameChanged,
                    label = { Text("Formula name") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = productDescription,
                    onValueChange = onProductDescriptionChanged,
                    label = { Text("Description") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = productQuantity.toString(),
                    onValueChange = { onProductQuantityChanged(it.toInt()) },
                    label = { Text("Quantity") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = productPrice.toString(),
                    onValueChange = { onProductPriceChanged(it.toDouble()) },
                    label = { Text("Price") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
                OutlinedTextField(
                    value = productImageUrl,
                    onValueChange = onProductImageUrlChanged,
                    label = { Text("ImageUrl") },
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.mediumSpacer)))
                Row {
                    Spacer(Modifier.weight(1F))
                    TextButton(onClick = onDismissRequest) {
                        Text(stringResource(R.string.annuleer))
                    }
                    Spacer(Modifier.width(dimensionResource(id = R.dimen.smallSpacer)))
                    TextButton(onClick = onProductSaved) {
                        Text(stringResource(R.string.bevestig))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateTaskPreview() {
    CreateProduct(
        productName = "",
        productDescription = "",
        productQuantity = 0,
        productPrice = 0.0,
        productImageUrl = "",

        onProductNameChanged = {},
        onProductDescriptionChanged = {},
        onProductQuantityChanged = {},
        onProductPriceChanged = {},
        onProductImageUrlChanged = {},
        onProductSaved = {},
        onDismissRequest = {},
    )
}
