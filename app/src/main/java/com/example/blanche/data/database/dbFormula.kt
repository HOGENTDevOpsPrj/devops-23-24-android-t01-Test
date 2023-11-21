package com.example.blanche.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.blanche.model.Formula

@Entity(tableName = "formulas")
data class dbFormula(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val nrOfDays: Int = 1,
    val price: Double = 250.0,
    val imageUrl: String = "imageurl",

)

fun dbFormula.asDomainTask(): Formula {
    return Formula(
        this.id,
        this.name,
        this.description,
        this.nrOfDays,
        this.price,
        this.imageUrl,
    )
}

fun Formula.asDbTask(): dbFormula {
    return dbFormula(
        name = this.name,
        description = this.description,
        nrOfDays = this.nrOfDays,
        price = this.price,
        imageUrl = this.imageUrl,
    )
}

fun List<dbFormula>.asDomainTasks(): List<Formula> {
    var formulaList = this.map {
        Formula(it.id, it.name, it.description, it.nrOfDays, it.price, it.imageUrl)
    }
    return formulaList
}
