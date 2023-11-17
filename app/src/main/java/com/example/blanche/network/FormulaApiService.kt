package com.example.blanche.network

import retrofit2.http.GET

// define what the API looks like
interface FormulaApiService {
    // suspend is added to force the user to call this in a coroutine scope
    @GET("tasks")
    suspend fun getFormulas(): List<ApiFormula>
}
