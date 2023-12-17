package com.example.blanche.ui.reservations

sealed interface ReservationApiState {
    object Success: ReservationApiState
    object Error: ReservationApiState
    object Loading: ReservationApiState
}