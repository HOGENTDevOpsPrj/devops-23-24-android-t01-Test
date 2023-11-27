package com.example.blanche.network

import com.example.blanche.model.Formula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
fun Flow<List<ApiFormula>>.asDomainObjects(): Flow<List<Formula>> {
    var domainList = this.map {
        it.asDomainObjects()
    }
    return domainList
}

fun List<ApiFormula>.asDomainObjects(): List<Formula> {
    var domainList = this.map {
        Formula(
            name = it.name,
            description = it.description,
            nrOfDays = it.nrOfDays,
            price = it.price,
            imageUrl = it.imageUrl,
        )
    }
    return domainList
}
