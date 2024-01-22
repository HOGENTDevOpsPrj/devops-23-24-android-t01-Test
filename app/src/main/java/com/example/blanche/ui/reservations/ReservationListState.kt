package com.example.blanche.ui.reservations

import com.example.blanche.model.Reservation
import com.example.blanche.model.ReservationItem

data class ReservationOverviewState(
    var reservationState: Int = 0,
    var reservationStateChanged: Boolean = false,
    val reservation: Reservation = Reservation(),
    var extraItemsList: MutableList<ReservationItem> = mutableListOf(),
    val scrollActionIdx: Int = 0,
    val scrollToItemIndex: Int = 0,
)

data class ReservationListState(val reservationList: List<Reservation> = listOf())
