package com.example.blanche.model

data class Formula(
    var id: Int,
    var name: String,
    var description: String,
    var nrOfDays: Int,
    var price: Double,
    var imageUrl: String,
)
