package com.example.blanche.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.blanche.model.Formula

@Entity(tableName = "formulas")
data class dbFormula(
    @PrimaryKey
    val id: String = "",
    val name: String = "",
    val description: String = "",
    //val nrOfDays: Int = 1,
    val price: Double = 1.0,
    val imageUrl: String = "imageurl",

)

fun dbFormula.asDomainFormula(): Formula {
    return Formula(
        this.id,
        this.name,
        this.description,
        //this.nrOfDays,
        this.price,
        this.imageUrl,
    )
}

fun Formula.asDbFormula(): dbFormula {
    return dbFormula(
        id = this.id,
        name = this.name,
        description = this.description,
        //nrOfDays = this.nrOfDays,
        price = this.price,
        imageUrl = this.imageUrl,
    )
}

fun List<dbFormula>.asDomainFormulas(): List<Formula> {
    var formulaList = this.map {
        Formula(it.id, it.name, it.description, it.price, it.imageUrl)
    }
    return formulaList
}
