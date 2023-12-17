package com.example.blanche.network.reservations

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ReservationApiService {
    @GET("/api/reservation")
    suspend fun getReservationsByState(@Query("state") state: Int): List<ApiReservation>
}

fun ReservationApiService.getReservationsAsFlow(): Flow<List<ApiReservation>> = flow {
    emit(getReservationsByState(0))
}
