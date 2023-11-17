package com.example.blanche.network

import com.example.blanche.model.Formula
import kotlinx.serialization.Serializable

@Serializable
data class ApiFormula(
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val nrOfDays: Int,
)

// extension function for an ApiFormula List to convert to a Domain Task List
fun List<ApiFormula>.asDomainObjects(): List<Formula> {
    var domainList = this.map {
        Formula(
            id = "", // Generate or retrieve the id appropriately
            name = it.name,
            description = it.description,
            nrOfDays = it.nrOfDays,
            price = it.price,
            imageUrl = it.imageUrl,
        )
    }
    return domainList
}
