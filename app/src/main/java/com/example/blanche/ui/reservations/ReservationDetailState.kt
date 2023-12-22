package com.example.blanche.ui.reservations

import com.example.blanche.model.Reservation
import com.example.blanche.model.ReservationItem

data class ReservationDetailState(
    val showFormulaDropDown: Boolean = false,
    val reservation: Reservation = Reservation(),
    val numberOfPersons: Int = 0,
    val items: List<ReservationItem> = emptyList()
)

data class ItemsListState(val itemsList: List<ReservationItem> = listOf())
