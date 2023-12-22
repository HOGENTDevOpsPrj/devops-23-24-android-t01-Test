package com.example.blanche.model

import kotlinx.serialization.Serializable

@Serializable
data class Beer(
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
)