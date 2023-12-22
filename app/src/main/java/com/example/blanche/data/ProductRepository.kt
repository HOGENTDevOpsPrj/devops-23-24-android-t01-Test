package com.example.blanche.data

import android.util.Log
import com.example.blanche.data.database.ProductDao
import com.example.blanche.data.database.asDbProduct
import com.example.blanche.data.database.asDomainFormulas
import com.example.blanche.data.database.asDomainProduct
import com.example.blanche.model.Product
import com.example.blanche.network.products.ProductApiService
import com.example.blanche.network.products.asDomainObjects
import com.example.blanche.network.products.getProductsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface ProductRepository {
    // all items from datasource
    fun getProducts(): Flow<List<Product>>
    // single item from datasource
    fun getProduct(id: String): Flow<Product?>
    suspend fun addProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun refresh()
}

class CachingProductRepository(
    private val productDao: ProductDao,
    private val productApiService: ProductApiService,
) : ProductRepository {

    // this repo contains logic to refresh the formulas (remote)
    // sometimes that logic is written in a 'usecase'
    override fun getProducts(): Flow<List<Product>> {
        // checks the array of items coming in
        // when empty --> tries to fetch from API
        return productDao.getAllItems().map {
            it.asDomainFormulas()
        }.onEach {
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    override fun getProduct(id: String): Flow<Product?> {
        return productDao.getItem(id).map {
            it.asDomainProduct()
        }
    }

    override suspend fun addProduct(product: Product) {
        // add to the local database
        productDao.insert(product.asDbProduct())

        // Make the API call to add the formula to the remote database
        try {
            //formulaApiService.addFormula(formula)
            Log.i("TEST", "Added formula to API: $product")
        } catch (e: Exception) {
            Log.e("TEST", "Error adding formula to API: $e")
            // Handle the error as needed
        }
    }


    override suspend fun deleteProduct(product: Product) {
        // Delete from the local database
        productDao.delete(product.asDbProduct())

        // Make the API call to delete the formula from the remote database
        try {
            productApiService.deleteProduct(product.id)
            Log.i("TEST", "Deleted formula from API: $product")
        } catch (e: Exception) {
            Log.e("TEST", "Error deleting formula from API: $e")
        }
    }

    override suspend fun updateProduct(product: Product) {
        productDao.update(product.asDbProduct())

        try {
            productApiService.updateProduct(product.id, product)
            Log.i("TEST", "Updated formula in API: $product")
        } catch (e: Exception) {
            Log.e("TEST", "Error updating formula in API: $e")
        }
    }


    override suspend fun refresh() {
        try {
            productApiService.getProductsAsFlow().asDomainObjects().collect { value ->
                for (product in value) {
                    Log.i("TEST", "Refresh: Adding formula - $product")
                    addProduct(product)
                }
            }
            Log.i("TEST", "Refresh: Data refreshed successfully.")
        } catch (e: Exception) {
            Log.e("TEST", "Refresh: Error - $e")
        }

    }
}