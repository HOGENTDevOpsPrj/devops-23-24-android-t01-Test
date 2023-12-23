package com.example.blanche.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.blanche.model.Formula

@Entity(tableName = "formulas")
@TypeConverters(
    Converters.MapTypeConverter::class
)
data class DbFormula(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String?,
    val hasDrinks: Boolean,
    val hasFood: Boolean,
    val pricePerDays: Map<Int, Double>,
    val pricePerExtraDay: Double,
)

fun DbFormula.asDomainFormula(): Formula {
    return Formula(
        this.id,
        this.name,
        this.description,
        this.imageUrl,
        this.hasDrinks,
        this.hasFood,
        this.pricePerDays,
        this.pricePerExtraDay,
    )
}

fun Formula.asDbFormula(): DbFormula {
    return DbFormula(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        hasDrinks = this.hasDrinks,
        hasFood = this.hasFood,
        pricePerDays = this.pricePerDays,
        pricePerExtraDay = this.pricePerExtraDay,
    )
}

fun List<DbFormula>.asDomainFormulas(): List<Formula> {
    var formulaList = this.map {
        Formula(
            it.id,
            it.name,
            it.description,
            it.imageUrl,
            it.hasDrinks,
            it.hasFood,
            it.pricePerDays,
            it.pricePerExtraDay,
        )
    }
    return formulaList
}
