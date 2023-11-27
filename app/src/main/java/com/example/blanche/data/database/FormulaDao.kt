package com.example.blanche.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FormulaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbFormula)

    @Update
    suspend fun update(item: dbFormula)

    @Delete
    suspend fun delete(item: dbFormula)

    @Query("SELECT * from formulas WHERE id = :id")
    fun getItem(id: Int): Flow<dbFormula>

    @Query("SELECT * from formulas ORDER BY name ASC")
    fun getAllItems(): Flow<List<dbFormula>>
}
