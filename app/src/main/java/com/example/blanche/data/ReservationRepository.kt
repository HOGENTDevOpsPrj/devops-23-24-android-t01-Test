package com.example.blanche.data

import android.util.Log
import com.example.blanche.data.database.ReservationDao
import com.example.blanche.data.database.asDbReservation
import com.example.blanche.data.database.asDomainReservations
import com.example.blanche.model.Reservation
import com.example.blanche.network.reservations.ReservationApiService
import com.example.blanche.network.reservations.asDomainObjects
import com.example.blanche.network.reservations.getReservationsAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.net.SocketTimeoutException

interface ReservationRepository {
    fun getReservationsByState(state: Int): Flow<List<Reservation>>

    suspend fun insertReservation(reservation: Reservation)

    suspend fun updateReservation(reservation: Reservation)

    suspend fun refresh()
}

class CachingReservationRepository(
    private val reservationDao: ReservationDao,
    private val reservationApiService: ReservationApiService
) : ReservationRepository {

    override fun getReservationsByState(state: Int): Flow<List<Reservation>> {
        return reservationDao.getReservationsByState(state = 5).map {
            it.asDomainReservations()
        }.onEach {
            if (it.isEmpty()) {
                refresh()
            }
        }
    }

    override suspend fun insertReservation(reservation: Reservation) {
        reservationDao.insert(reservation.asDbReservation())
    }

    override suspend fun updateReservation(reservation: Reservation) {
        reservationDao.update(reservation.asDbReservation())
        try {
            reservationApiService.updateReservation(reservation)
        }
        catch (e: Exception) {
            Log.e("Error", "Error updating reservation in API: $e")
        }
    }

    override suspend fun refresh() {
        try {
            reservationApiService.getReservationsAsFlow().asDomainObjects().collect() {
                value ->
                println(value)
                for (reservation in value) {
                    insertReservation(reservation)
                }
            }
        }
        catch(e: SocketTimeoutException){
            Log.i("Error", "$e")
        }
    }
}


