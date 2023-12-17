package com.example.blanche.model

import java.util.Date

data class Reservation(
    var startDate: Date,
    var endDate: Date,
    var totalPrice: Double,
    var state: String,
    var numberOfPersons: Int,
    var customer: Customer,
    var invoices: List<Invoice>,
    var formula: Formula,
    var typeOfBeer: Beer,
)