package com.example.blanche.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    var id: String,
    var name: String,
    var description: String,
    var price: Double,
    var imageUrl: String,
    var quantityInStock: Int,
    var minimumUnits: Int,
)