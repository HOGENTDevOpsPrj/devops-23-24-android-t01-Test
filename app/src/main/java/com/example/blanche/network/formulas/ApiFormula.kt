package com.example.blanche.network.formulas

import com.example.blanche.model.Formula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiFormula(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val hasDrinks: Boolean,
    val hasFood: Boolean,
    val pricePerDays: HashMap<Int, Double>,
    val pricePerExtraDay: Double,
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
            id = it.id,
            name = it.name,
            description = it.description,
            price = it.price,
            imageUrl = it.imageUrl,
            hasDrinks = it.hasDrinks,
            hasFood = it.hasFood,
            pricePerDays = it.pricePerDays,
            pricePerExtraDay = it.pricePerExtraDay
        )
    }
    return domainList
}
