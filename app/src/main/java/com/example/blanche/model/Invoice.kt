package com.example.blanche.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Invoice(
    var invoiceNumber: String,
    @Contextual
    var confirmationDate: LocalDateTime,
    @Contextual
    var expirationDate: LocalDateTime,
    var isQuote: Boolean,
    var isPayed: Boolean,
)