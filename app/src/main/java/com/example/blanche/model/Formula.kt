package com.example.blanche.model

import kotlinx.serialization.Serializable

@Serializable
data class Formula(
    val id: String = "",
    var name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val hasDrinks: Boolean = false,
    val hasFood: Boolean = false,
    val pricePerDays: Map<Int, Double> = emptyMap(),
    val pricePerExtraDay: Double = 0.0,
)
