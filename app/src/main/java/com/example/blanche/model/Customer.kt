package com.example.blanche.model

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var customerAddress: Address,
    var email: EmailAddress,
)