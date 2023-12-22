package com.example.blanche.model

import kotlinx.serialization.Serializable

@Serializable
data class ReservationItem(
    var id: String,
    var reservationId: String,
    var product: Product,
    var quantity: Int,
    var price: Double,
)