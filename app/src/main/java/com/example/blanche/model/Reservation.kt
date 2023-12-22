package com.example.blanche.model

data class Reservation(
    val id: String = "",
    var startDate: String = "",
    var endDate: String = "",
    val totalPrice: Double = 0.0,
    val state: Int = 0,
    var numberOfPersons: Int = 0,
    val customer: Customer = Customer(),
    var items: List<ReservationItem> = emptyList(),
    val invoices: List<Invoice> = emptyList(),
    var formula: Formula = Formula(),
    val typeOfBeer: Beer? = Beer(),
    val notes: String = "",
)