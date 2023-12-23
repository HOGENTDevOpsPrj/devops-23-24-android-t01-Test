package com.example.blanche.model

import com.example.blanche.network.formulas.ApiFormula
import kotlinx.serialization.Serializable

@Serializable
data class Formula(
    val id: String = "",
    var name: String = "",
    val description: String = "",
    val imageUrl: String? = "",
    val hasDrinks: Boolean = false,
    val hasFood: Boolean = false,
    val pricePerDays: Map<Int, Double> = emptyMap(),
    val pricePerExtraDay: Double = 0.0,
)

fun List<Formula>.asApiObjects(): List<ApiFormula>  {
    var formulas = this.map {
        it.asApiObject()
    }
    return formulas
}

fun  Formula.asApiObject():  ApiFormula {
    return ApiFormula(
            id =  id,
            name = name,
            description = description,
            imageUrl = imageUrl,
            hasDrinks = hasDrinks,
            hasFood = hasFood,
            pricePerDays = HashMap(pricePerDays),
            pricePerExtraDay = pricePerExtraDay
        )

}