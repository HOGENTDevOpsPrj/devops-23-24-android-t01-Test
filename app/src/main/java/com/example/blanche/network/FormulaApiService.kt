package com.example.blanche.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET

// define what the API looks like
interface FormulaApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("/api/formula")
    suspend fun getFormulas(): List<ApiFormula>
}

// helper function
fun FormulaApiService.getFormulasAsFlow(): Flow<List<ApiFormula>> = flow {
    emit(getFormulas())
}
