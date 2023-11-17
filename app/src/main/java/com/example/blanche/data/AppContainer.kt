package com.example.blanche.data

import com.example.blanche.network.FormulaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val formulaRepository: FormulaRepository
}

// container that takes care of dependencies
class DefaultAppContainer() : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: FormulaApiService by lazy {
        retrofit.create(FormulaApiService::class.java)
    }

    override val formulaRepository: FormulaRepository by lazy {
        ApiFormulasRepository(retrofitService)
    }
}
