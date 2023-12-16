package com.example.blanche.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.blanche.model.Formula
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "formulas")
@TypeConverters(MapTypeConverter::class)
data class dbFormula(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val hasDrinks: Boolean,
    val hasFood: Boolean,
    @TypeConverters(MapTypeConverter::class)
    val pricePerDays: Map<Int, Double>,
    val pricePerExtraDay: Double,
)

fun dbFormula.asDomainFormula(): Formula {
    return Formula(
        this.id,
        this.name,
        this.description,
        this.price,
        this.imageUrl,
        this.hasDrinks,
        this.hasFood,
        this.pricePerDays,
        this.pricePerExtraDay,
    )
}

fun Formula.asDbFormula(): dbFormula {
    return dbFormula(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        imageUrl = this.imageUrl,
        hasDrinks = this.hasDrinks,
        hasFood = this.hasFood,
        pricePerDays = this.pricePerDays,
        pricePerExtraDay = this.pricePerExtraDay,
    )
}

fun List<dbFormula>.asDomainFormulas(): List<Formula> {
    var formulaList = this.map {
        Formula(
            it.id,
            it.name,
            it.description,
            it.price,
            it.imageUrl,
            it.hasDrinks,
            it.hasFood,
            it.pricePerDays,
            it.pricePerExtraDay,
        )
    }
    return formulaList
}

object MapTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String): Map<Int, Double> =
        Gson().fromJson(value,  object : TypeToken<Map<Int, Double>>() {}.type)

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<Int, Double>?): String =
        if(value == null) "" else Gson().toJson(value)
}
