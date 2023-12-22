package com.example.blanche.data

import android.content.Context
import com.example.blanche.data.database.BlancheDb
import com.example.blanche.network.formulas.FormulaApiService
import com.example.blanche.network.reservations.ReservationApiService
import com.google.gson.GsonBuilder
import com.example.blanche.network.products.ProductApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

interface AppContainer {
    val formulaRepository: FormulaRepository
    val reservationRepository: ReservationRepository
    val productRepository: ProductRepository
}

// container that takes care of dependencies
class DefaultAppContainer(private val context: Context) : AppContainer {

    private val gson = GsonBuilder().setLenient().create()
    private val baseUrl = "http://10.0.2.2:65498"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val retrofitService: FormulaApiService by lazy {
        retrofit.create(FormulaApiService::class.java)
    }

    override val formulaRepository: FormulaRepository by lazy {
        CachingFormulaRepository(BlancheDb.getDatabase(context = context).formulaDao(), retrofitService)
    }

    private val reservationRetrofitService: ReservationApiService by lazy {
        retrofit.create(ReservationApiService::class.java)
    }

    override val reservationRepository: ReservationRepository by lazy {
        CachingReservationRepository(BlancheDb.getDatabase(context = context).reservationDao(), reservationRetrofitService)
    }

    private val retrofitProductService: ProductApiService by lazy{
        retrofit.create(ProductApiService::class.java)
    }

    override val productRepository: ProductRepository by lazy{
        CachingProductRepository(
            BlancheDb.getDatabase(context = context).productDao(),
            retrofitProductService
        )
    }
}
