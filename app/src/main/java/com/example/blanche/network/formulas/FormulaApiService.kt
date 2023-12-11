package com.example.blanche.network.formulas

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

// define what the API looks like
interface FormulaApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("/api/formula")
    suspend fun getFormulas(): List<ApiFormula>

    @GET("/api/formula/{id}")
    suspend fun getFormula(id: String): ApiFormula

    @POST("/api/formula")
    suspend fun addFormula(formula: ApiFormula)

    @PUT("/api/formula/{id}")
    suspend fun updateFormula(id: String, formula: ApiFormula)

    @DELETE("/api/formula/{id}")
    suspend fun deleteFormula(id: String)
}

// helper function
fun FormulaApiService.getFormulasAsFlow(): Flow<List<ApiFormula>> = flow {
    emit(getFormulas())
}