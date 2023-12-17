package com.example.blanche.ui.reservations

import com.example.blanche.model.Reservation

data class ReservationOverviewState(
    val reservationStatus: String = ""
)

data class ReservationListState(val reservationList: List<Reservation> = listOf())
