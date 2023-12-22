package com.example.blanche.ui.reservations

import com.example.blanche.model.Reservation
import com.example.blanche.model.ReservationItem

data class ReservationOverviewState(
    val reservationStatus: String = "",
    var extraItemsList: MutableList<ReservationItem> = mutableListOf(),
)

data class ReservationListState(val reservationList: List<Reservation> = listOf())
