package com.example.blanche.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.blanche.model.Address
import com.example.blanche.model.Beer
import com.example.blanche.model.Customer
import com.example.blanche.model.EmailAddress
import com.example.blanche.model.Formula
import com.example.blanche.model.Invoice
import com.example.blanche.model.Reservation

@Entity(tableName = "reservations")
@TypeConverters(
    Converters.DateTypeConverter::class,
    Converters.CustomerTypeConverter::class,
    Converters.FormulaTypeConverter::class,
    Converters.BeerTypeConverter::class,
    Converters.InvoiceListTypeConverter::class
)
data class DbReservation(
    @PrimaryKey
    val id: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val totalPrice: Double = 0.0,
    val state: Int = 0,
    val numberOfPersons: Int = 0,
    val customer: Customer = Customer("", "", "", Address("", "", "", "", ""), EmailAddress("")),
    val invoices: List<Invoice> = emptyList(),
    val formula: Formula = Formula("", "", "", 0.0, "", false, false, hashMapOf(0 to 0.0), 0.0),
    val typeOfBeer: Beer? = Beer("", "", 0.0),
)

fun DbReservation.asDomainReservation(): Reservation {
    return Reservation(
        id,
        startDate,
        endDate,
        totalPrice,
        state,
        numberOfPersons,
        customer,
        invoices,
        formula,
        typeOfBeer
    )
}

fun Reservation.asDbReservation(): DbReservation {
    return DbReservation(
        id,
        startDate,
        endDate,
        totalPrice,
        state,
        numberOfPersons,
        customer,
        invoices,
        formula,
        typeOfBeer
    )
}

fun List<DbReservation>.asDomainReservations(): List<Reservation> {
    var reservationList = map {
        Reservation(
            it.id,
            it.startDate,
            it.endDate,
            it.totalPrice,
            it.state,
            it.numberOfPersons,
            it.customer,
            it.invoices,
            it.formula,
            it.typeOfBeer
        )
    }
    return reservationList
}