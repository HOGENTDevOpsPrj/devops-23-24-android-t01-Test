package com.example.blanche.model

import java.util.Date

data class Invoice(
    var invoiceNumber: String,
    var confirmationDate: Date,
    var expirationDate: Date,
    var isQuote: Boolean,
    var isPayed: Boolean,
)