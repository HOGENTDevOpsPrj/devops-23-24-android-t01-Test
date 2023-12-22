package com.example.blanche.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.blanche.model.Product

@Entity(tableName = "products")
data class dbProduct(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val quantityInStock: Int,
    val minimumUnits: Int,
)

fun dbProduct.asDomainProduct(): Product {
    return Product(
        this.id,
        this.name,
        this.description,
        this.price,
        this.imageUrl,
        this.quantityInStock,
        this.minimumUnits,
    )
}

fun Product.asDbProduct(): dbProduct {
    return dbProduct(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        imageUrl = this.imageUrl,
        quantityInStock = this.quantityInStock,
        minimumUnits = this.minimumUnits,
    )
}

fun List<dbProduct>.asDomainFormulas(): List<Product> {
    var productList = this.map {
        Product(
            it.id,
            it.name,
            it.description,
            it.price,
            it.imageUrl,
            it.quantityInStock,
            it.minimumUnits,
        )
    }
    return productList
}