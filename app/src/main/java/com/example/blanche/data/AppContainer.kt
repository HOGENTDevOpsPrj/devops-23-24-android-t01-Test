package com.example.blanche.data

import android.content.Context
import com.example.blanche.data.database.BlancheDb
import com.example.blanche.network.formulas.FormulaApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val formulaRepository: FormulaRepository
}

// container that takes care of dependencies
class DefaultAppContainer(private val context: Context) : AppContainer {

    private val baseUrl = "http://10.0.2.2:65498"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json.asConverterFactory("application/json".toMediaType()),
        )
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: FormulaApiService by lazy {
        retrofit.create(FormulaApiService::class.java)
    }

    /*override val formulaRepository: FormulaRepository by lazy {
        ApiFormulasRepository(retrofitService)
    }
    */
    override val formulaRepository: FormulaRepository by lazy {
        CachingFormulaRepository(
            BlancheDb.getDatabase(context = context).formulaDao(),
            retrofitService)
    }
}
