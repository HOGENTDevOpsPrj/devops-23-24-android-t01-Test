package com.example.blanche.ui.products

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blanche.R
import com.example.blanche.model.Product
import com.example.blanche.network.products.ApiProduct

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    viewModel: ProductScreenOverview,
) {
    ElevatedCard {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val color by animateColorAsState(
            targetValue = if (expanded) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onPrimary
            },
            label = "colorAnimation",
        )
        Row(
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium,
                    ),
                )
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .background(color)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Row {
                    Text(
                        text = product.name,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodyLarge,
                        textDecoration = TextDecoration.None,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            viewModel.toggleEditProductScreen()
                            viewModel.setProduct(product) },
                        modifier = Modifier.align(Alignment.CenterVertically),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            contentDescription = stringResource(R.string.edit_formula_content_description),
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.deleteProduct(product)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            contentDescription = stringResource(R.string.delete_formula_content_description),
                        )
                    }
                    FormulaItemButton(
                        expanded = expanded,
                        onClick = { expanded = !expanded },
                        modifier = Modifier.align(Alignment.Top),
                    )
                }
                if (expanded) {
                    Text(
                        text = product.description,
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Huurprijs: ${product.price} €",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    /*                    Text(
                                            text = formula.imageUrl,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )*/
                }
            }

        }
    }

    /*    val showEditScreen = viewModel.showEditFormulaScreen.collectAsState().value

        if (showEditScreen) {
            Dialog(onDismissRequest = { *//*TODO*//* }) {
            ElevatedCard(

            ) {
                EditFormulaScreen(
                    formula = formula,
                    viewModel = viewModel,
                    onNavigationUp = { viewModel.toggleEditFormulaScreen() }
                )
            }
        }
    }*/
}

@Composable
fun FormulaItemButton(expanded: Boolean, onClick: () -> Unit, modifier: Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
        )
    }
}

@Composable
fun EditProductScreen(
    product: Product,
    viewModel: ProductScreenOverview,
    onNavigationUp: () -> Unit
) {
    var editedName by remember { mutableStateOf(product.name) }
    var editedDescription by remember { mutableStateOf(product.description) }
    var editedPrice by remember { mutableDoubleStateOf(product.price) }
    var editedImageUrl by remember { mutableStateOf(product.imageUrl) }
    var editedQuantity by remember { mutableStateOf(product.quantityInStock) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        EditTextField(
            value = editedName,
            onValueChange = { editedName = it },
            label = "Name"
        )
        EditTextField(
            value = editedDescription,
            onValueChange = { editedDescription = it },
            label = "Description"
        )
        EditTextField(
            value = editedPrice.toString(),
            onValueChange = { editedPrice = it.toDouble() },
            label = "Price"
        )
        EditTextField(
            value = editedQuantity.toString(),
            onValueChange = { editedQuantity = it.toInt() },
            label = "Quantity"
        )
        EditTextField(
            value = editedImageUrl,
            onValueChange = { editedImageUrl = it },
            label = "Image URL"
        )

        EditButton(
            onClick = {
                val editedProduct = product.copy(
                    name = editedName,
                    description = editedDescription,
                    price = editedPrice,
                    quantityInStock = editedQuantity,
                    imageUrl = editedImageUrl,
                )
                viewModel.editProduct(editedProduct)
                onNavigationUp()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun EditButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(top = 16.dp)
    ) {
        Text("Save")
    }
}


/*@Preview
@Composable
fun ProductItemPreview() {
    ProductItem(
        name = "Product name",
        description = "Product description",
        quantityInStock = 5,
        price = 50.0,
        imageUrl = "dit_met_een_lange_url_voorstellen",
        product = ApiProduct(
            id = "id",
            name = "name",
            description = "description",
            price = 50.0,
            imageUrl = "imageUrl",
            quantityInStock = 5,
        ),
        onClick = {},
        onDeleteClick = {},
        viewModel = ProductScreenOverview(),
    )
}*/

