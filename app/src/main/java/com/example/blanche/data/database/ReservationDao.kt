package com.example.blanche.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reservation: DbReservation)

    @Update
    suspend fun update(reservation: DbReservation)

    @Delete
    suspend fun delete(reservation: DbReservation)

    @Query("SELECT * from reservations WHERE state = :state ORDER BY startDate")
    fun getReservationsByState(state: Int): Flow<List<DbReservation>>
}
