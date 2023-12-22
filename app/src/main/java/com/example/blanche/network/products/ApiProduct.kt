package com.example.blanche.network.products

import com.example.blanche.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiProduct(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val quantityInStock: Int,
    val minimumUnits: Int,
)

fun Flow<List<ApiProduct>>.asDomainObjects(): Flow<List<Product>> {
    var domainList = this.map {
        it.asDomainObjects()
    }
    return domainList
}

fun List<ApiProduct>.asDomainObjects(): List<Product> {
    var domainList = this.map {
        Product(
            id = it.id,
            name = it.name,
            description = it.description,
            price = it.price,
            imageUrl = it.imageUrl,
            quantityInStock = it.quantityInStock,
            minimumUnits =  it.minimumUnits,
        )
    }
    return domainList
}