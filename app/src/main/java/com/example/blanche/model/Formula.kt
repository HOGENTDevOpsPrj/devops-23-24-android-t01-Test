package com.example.blanche.model

data class Formula(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val hasDrinks: Boolean,
    val hasFood: Boolean,
    val pricePerDays: Map<Int, Double>,
    val pricePerExtraDay: Double,
)
