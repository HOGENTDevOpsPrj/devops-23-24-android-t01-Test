package com.example.blanche.network.products

import com.example.blanche.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("/api/product")
    suspend fun getProducts(): List<ApiProduct>

    @GET("/api/product/{id}")
    suspend fun getFormula(@Path("id") id: String): ApiProduct

    @POST("/api/product")
    suspend fun addProduct(@Body product: ApiProduct)

    @PUT("/api/product/{id}")
    suspend fun updateProduct(@Path("id") id: String, @Body product: Product)

    @DELETE("/api/formula/{id}")
    suspend fun deleteProduct(@Path("id") id: String): Response<Unit>
}

// helper function
fun ProductApiService.getProductsAsFlow(): Flow<List<ApiProduct>> = flow {
    emit(getProducts())
}