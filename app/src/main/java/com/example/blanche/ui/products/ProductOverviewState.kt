package com.example.blanche.ui.products

import com.example.blanche.model.Product

// the data class just holds the (immutable) values of the state
data class ProductOverviewState(
    val newProductId: String = "",
    val newProductName: String = "",
    val newProductDescription: String = "",
    val newProductQuantity: Int = 0,
    val newProductPrice: Double = 0.0,
    val newProductImageUrl: String = "",
    val newProductMinimumUnits: Int = 0,
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

data class ProductListState(val productList: List<Product> = listOf())

// the sealed interface has only three possible values
/*Sidenote: to learn more about this TaskApiState object, you can search on LCE (Loading, Content, Error) pattern
When the state is changed to Error, the taskList will not be updated (offline first).
To ensure the list is considered immutable (fully immutable, won't ever change unless a new object is created), add the Immutable annotation.
The LCE pattern is not completed in the application, because it requires more complex helper classes
An example can be found here https://www.valueof.io/blog/compose-ui-state-flow-offline-first-repository
*/

sealed interface ProductApiState {
    object Success : ProductApiState
    object Error : ProductApiState
    object Loading : ProductApiState
}
