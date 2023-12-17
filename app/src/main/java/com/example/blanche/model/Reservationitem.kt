package com.example.blanche.model

data class Reservationitem(
    var id: String,
    var reservationId: String,
    var product: String,
    var quantity: Int,
    var price: Int,
)