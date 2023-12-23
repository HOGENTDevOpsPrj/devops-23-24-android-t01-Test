package com.example.blanche.network.formulas

import com.example.blanche.model.Formula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
 import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// define what the API looks like
interface FormulaApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("/api/formula")
    suspend fun getFormulas(): List<ApiFormula>

    @POST("/api/formula")
    suspend fun addFormula(@Body formula: ApiFormula): Response<String>

    @PUT("/api/formula/{id}")
    suspend fun updateFormula(@Path("id") id: String, @Body formula: Formula): Response<Unit>

    @DELETE("/api/formula/{id}")
    suspend fun deleteFormula(@Path("id") id: String): Response<Unit>
}

// helper function
fun FormulaApiService.getFormulasAsFlow(): Flow<List<ApiFormula>> = flow {
    emit(getFormulas())
}
