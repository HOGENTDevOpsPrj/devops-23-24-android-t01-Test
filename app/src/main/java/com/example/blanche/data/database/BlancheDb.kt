package com.example.blanche.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [DbFormula::class, DbReservation::class], version = 5, exportSchema = false)
abstract class BlancheDb : RoomDatabase() {

    abstract fun formulaDao(): FormulaDao
    abstract fun reservationDao(): ReservationDao

    companion object {
        @Volatile
        private var Instance: BlancheDb? = null

        fun getDatabase(context: Context): BlancheDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, BlancheDb::class.java, "blanche_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
