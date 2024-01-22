package com.example.blanche.model

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val street: String = "",
    val number: String = "",
    val postalCode: String = "",
    val city: String = "",
    val country: String = "",
)