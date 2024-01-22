package com.example.blanche.ui.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.blanche.BlancheAdminApplication
import com.example.blanche.data.ProductRepository
import com.example.blanche.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException


class ProductScreenOverview(private val productRepository: ProductRepository) : ViewModel() {
    // use StateFlow (Flow: emits current state + any updates)
    /*
    * Note: uiState is a cold flow. Changes don't come in from above unless a
    * refresh is called...
    * */
    private val _uiState = MutableStateFlow(ProductOverviewState())
    val uiState: StateFlow<ProductOverviewState> = _uiState.asStateFlow()

    private val _showEditProductScreen = MutableStateFlow(false)
    val showEditProductScreen: StateFlow<Boolean> = _showEditProductScreen

    fun toggleEditProductScreen() {
        _showEditProductScreen.value = !_showEditProductScreen.value
    }

    fun setProduct(product: Product) {
        setNewProductName(product.name)
        setNewProductDescription(product.description)
        setNewProductPrice((product.price))
        setNewProductQuantity(product.quantityInStock)
    }

    /*
  * Note: uiListState is a hot flow (.stateIn makes it so) --> it updates given a scope (viewmodelscope)
  * when no updates are required (lifecycle) the subscription is stopped after a timeout
  * */
    lateinit var uiListState: StateFlow<ProductListState>

    // keeping the state of the api request
    var productApiState: ProductApiState by mutableStateOf(ProductApiState.Loading)
        private set

    init {
        // initializes the uiListState
        getRepoProducts()
    }

    fun editProduct(product: Product){
        viewModelScope.launch {
            productRepository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch {
            productRepository.deleteProduct(product)
        }
    }
    /*
        var showDeleteConfirmDialog by mutableStateOf(false)
            private set

        fun confirmDeleteFormula(formula: Formula){
            showDeleteConfirmDialog = true
        }

        fun cancelDeleteFormula(){
            showDeleteConfirmDialog = false
        }

        */

    fun addProduct() {
        // saving the new task (to db? to network? --> doesn't matter
        viewModelScope.launch {
            saveProduct(
                Product(
                    _uiState.value.newProductId,
                    _uiState.value.newProductName,
                    _uiState.value.newProductDescription,
                    _uiState.value.newProductPrice,
                    _uiState.value.newProductImageUrl,
                    _uiState.value.newProductQuantity,
                    _uiState.value.newProductMinimumUnits
                ),
            )
        }
        _uiState.update { currentState ->
            currentState.copy(
                newProductName = "",
                newProductDescription = "",
                newProductPrice = 0.0,
                newProductImageUrl = "",
                newProductQuantity = 0,
                scrollActionIdx = currentState.scrollActionIdx.plus(1),
                scrollToItemIndex = uiListState.value.productList.size,
            )
        }
    }

    private fun validateInput(): Boolean {
        return with(_uiState) {
            value.newProductName.isNotBlank() && value.newProductDescription.isNotBlank() &&
                    value.newProductQuantity > 0 && value.newProductPrice > 0.0 && value.newProductImageUrl.isNotBlank()
        }
    }

    fun setNewProductName(newName: String) {
        _uiState.update { it.copy(newProductName = newName) }
    }

    fun setNewProductDescription(newProductDescription: String) {
        _uiState.update {
            it.copy(newProductDescription = newProductDescription)
        }
    }

    fun setNewProductQuantity(newProductQuantity: Int) {
        _uiState.update {
            it.copy(newProductQuantity = newProductQuantity)
        }
    }

    fun setNewProductPrice(newProductPrice: Double) {
        _uiState.update {
            it.copy(newProductPrice = newProductPrice)
        }
    }

    fun setNewProductImageUrl(newProductImageUrl: String) {
        _uiState.update {
            it.copy(newProductImageUrl = newProductImageUrl)
        }
    }

    // this
    private fun getRepoProducts() {
        try {
            viewModelScope.launch { productRepository.refresh() }

            uiListState = productRepository.getProducts().map { ProductListState(it) }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = ProductListState(),
                )
            productApiState = ProductApiState.Success
        } catch (e: IOException) {
            // show a toast? save a log on firebase? ...
            // set the error state
            productApiState = ProductApiState.Error
        }
    }

    private suspend fun saveProduct(product: Product) {
        if (validateInput()) {
            productRepository.addProduct(product)
        }
    }

    // object to tell the android framework how to handle the parameter of the viewmodel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BlancheAdminApplication)
                val productRepository = application.container.productRepository
                ProductScreenOverview(
                    productRepository = productRepository,
                )
            }
        }
    }
}