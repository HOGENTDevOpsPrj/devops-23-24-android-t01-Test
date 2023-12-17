package com.example.blanche.model

import kotlinx.serialization.Serializable

@Serializable
data class EmailAddress(
    val email: String
)