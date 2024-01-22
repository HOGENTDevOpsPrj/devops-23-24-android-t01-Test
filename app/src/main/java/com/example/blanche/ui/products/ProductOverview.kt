package com.example.blanche.ui.products


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
fun ProductOverview(
    modifier: Modifier = Modifier,
    productScreenOverview: ProductScreenOverview = viewModel(factory = ProductScreenOverview.Factory),
    isAddingVisible: Boolean = false,
    makeInvisible: () -> Unit = {},
) {
    Log.i("vm inspection", "FormulaOverview composition")
    val productOverviewState by productScreenOverview.uiState.collectAsState()
    val productListState by productScreenOverview.uiListState.collectAsState()

    // use the ApiState
    val productApiState = productScreenOverview.productApiState

    val showEditScreen = productScreenOverview.showEditProductScreen.collectAsState().value

    Box(modifier = modifier) {
        when (productApiState) {
            is ProductApiState.Loading -> Text("Loading...")
            is ProductApiState.Error -> Text("Couldn't load...")
            is ProductApiState.Success -> ProductListComponent(productOverviewState = productOverviewState, productListState = productListState, modifier = modifier)
        }

        if (isAddingVisible) {
            CreateProduct(
                productName = productOverviewState.newProductName,
                productDescription = productOverviewState.newProductDescription,
                productQuantity = productOverviewState.newProductQuantity,
                productPrice = productOverviewState.newProductPrice,
                productImageUrl = productOverviewState.newProductImageUrl,
                onProductNameChanged = { productScreenOverview.setNewProductName(it) },
                onProductDescriptionChanged = { productScreenOverview.setNewProductDescription(it) },
                onProductQuantityChanged = { productScreenOverview.setNewProductQuantity(it) },
                onProductPriceChanged = { productScreenOverview.setNewProductPrice(it) },
                onProductImageUrlChanged = { productScreenOverview.setNewProductImageUrl(it) },

                onProductSaved = {
                    productScreenOverview.addProduct()
                    makeInvisible()
                },
                onDismissRequest = { makeInvisible },
            )
        }

        if (showEditScreen) {
            Dialog(onDismissRequest = { }) {
                EditProduct(
                    productName = productOverviewState.newProductName,
                    productDescription = productOverviewState.newProductDescription,
                    productPrice = productOverviewState.newProductPrice,
                    productQuantity = productOverviewState.newProductQuantity,
                    onProductNameChanged = { productScreenOverview.setNewProductName(it) },
                    onProductDescriptionChanged = { productScreenOverview.setNewProductDescription(it) },
                    onProductPriceChanged = { productScreenOverview.setNewProductPrice(it) },
                    onProductQuantityChanged = {productScreenOverview.setNewProductQuantity(it)},
                    onProductSaved = {
                        productScreenOverview.addProduct()
                        makeInvisible()
                    },
                    onDismissRequest = { productScreenOverview.toggleEditProductScreen() },
                )
            }
        }
    }
}

@Composable
fun ProductListComponent(
    modifier: Modifier = Modifier,
    productOverviewState: ProductOverviewState,
    productListState: ProductListState,
) {
    val viewModel: ProductScreenOverview = viewModel(factory = ProductScreenOverview.Factory,)
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState, modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        items(productListState.productList) {
            ProductItem(product = it, viewModel = viewModel)
            Spacer(Modifier.height(dimensionResource(id = R.dimen.smallSpacer)))
        }

    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productOverviewState.scrollActionIdx) {
        if (productOverviewState.scrollActionIdx != 0) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(productOverviewState.scrollToItemIndex)
            }
        }
    }
}
