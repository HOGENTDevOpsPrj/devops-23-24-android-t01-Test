package com.example.blanche.data.database

import androidx.room.TypeConverter
import com.example.blanche.model.Beer
import com.example.blanche.model.Customer
import com.example.blanche.model.Formula
import com.example.blanche.model.Invoice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDateTime

class Converters {
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

    object DateTypeConverter {
        @TypeConverter
        @JvmStatic
        fun stringToDate(value: String): LocalDateTime =
            Gson().fromJson(value,  object : TypeToken<LocalDateTime>() {}.type)

        @TypeConverter
        @JvmStatic
        fun dateToString(value: LocalDateTime?): String =
            if(value == null) "" else Gson().toJson(value)
    }

    object CustomerTypeConverter {
        @TypeConverter
        @JvmStatic
        fun stringToCustomer(value: String): Customer =
            Gson().fromJson(value,  object : TypeToken<Customer>() {}.type)

        @TypeConverter
        @JvmStatic
        fun customerToString(value: Customer?): String =
            if(value == null) "" else Gson().toJson(value)
    }

    object FormulaTypeConverter {
        @TypeConverter
        @JvmStatic
        fun stringToFormula(value: String): Formula =
            Gson().fromJson(value,  object : TypeToken<Formula>() {}.type)

        @TypeConverter
        @JvmStatic
        fun formulaToString(value: Formula?): String =
            if(value == null) "" else Gson().toJson(value)
    }

    object BeerTypeConverter {
        @TypeConverter
        @JvmStatic
        fun stringToBeer(value: String): Beer? =
            Gson().fromJson(value,  object : TypeToken<Beer?>() {}.type)

        @TypeConverter
        @JvmStatic
        fun beerToString(value: Beer?): String =
            if(value == null) "" else Gson().toJson(value)
    }

    object InvoiceListTypeConverter {
        @TypeConverter
        @JvmStatic
        fun stringToInvoiceList(value: String): List<Invoice> =
            Gson().fromJson(value,  object : TypeToken<List<Invoice>>() {}.type)

        @TypeConverter
        @JvmStatic
        fun invoiceListToString(value: List<Invoice>?): String =
            if(value == null) "" else Gson().toJson(value)
    }
}