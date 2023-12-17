package com.example.blanche.network.reservations

import com.example.blanche.model.Beer
import com.example.blanche.model.Customer
import com.example.blanche.model.Formula
import com.example.blanche.model.Invoice
import com.example.blanche.model.Reservation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable

@Serializable
data class ApiReservation(
    val id: String,
    val startDate: String,
    val endDate: String,
    val totalPrice: Double,
    val state: Int,
    val numberOfPersons: Int,
    val customer: Customer,
    //val items: List<@Contextual Reservationitem>,
    val invoices: List<Invoice>,
    val formula: Formula,
    val typeOfBeer: Beer,
)

fun Flow<List<ApiReservation>>.asDomainObjects(): Flow<List<Reservation>> {
    var domainList = this.map {
        it.asDomainObjects()
    }
    return domainList
}

fun List<ApiReservation>.asDomainObjects(): List<Reservation> {
    var domainList = this.map {
        Reservation(
            it.id,
            it.startDate,
            it.endDate,
            it.totalPrice,
            it.state,
            it.numberOfPersons,
            it.customer,
           // it.items,
            it.invoices,
            it.formula,
            it.typeOfBeer
        )
    }
    return domainList
}
