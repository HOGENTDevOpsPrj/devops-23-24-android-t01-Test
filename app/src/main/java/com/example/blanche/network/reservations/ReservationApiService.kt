package com.example.blanche.network.reservations

import com.example.blanche.model.Reservation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ReservationApiService {
    @GET("/api/reservation")
    suspend fun getReservationsByState(@Query("state") state: Int): List<ApiReservation>

    @PUT("/api/reservation")
    suspend fun updateReservation(@Body reservation: Reservation): Response<Unit>
}

fun ReservationApiService.getReservationsAsFlow(): Flow<List<ApiReservation>> = flow {
    emit(getReservationsByState(state = 5))
}
