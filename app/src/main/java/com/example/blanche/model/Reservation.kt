package com.example.blanche.model

data class Reservation(
    val id: String = "",
    var startDate: String = "",
    var endDate: String = "",
    var totalPrice: Double = 0.0,
    var state: Int = 0,
    var numberOfPersons: Int = 0,
    val customer: Customer = Customer(),
    var items: List<ReservationItem> = emptyList(),
    val invoices: List<Invoice> = emptyList(),
    var formula: Formula = Formula(),
    val typeOfBeer: Beer? = Beer(),
    val notes: String? = "",
)