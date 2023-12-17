package com.example.blanche.model

data class Reservation(
    val id: String,
    val startDate: String,
    val endDate: String,
    val totalPrice: Double,
    val state: Int,
    val numberOfPersons: Int,
    val customer: Customer,
    //var items: List<Reservationitem>,
    val invoices: List<Invoice>,
    val formula: Formula,
    val typeOfBeer: Beer?,
)