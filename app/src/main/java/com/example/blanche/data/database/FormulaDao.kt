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
    suspend fun insert(item: DbFormula)

    @Update
    suspend fun update(item: DbFormula)

    @Delete
    suspend fun delete(item: DbFormula)


    @Query("DELETE from formulas")
    suspend fun deleteAll()

    @Query("SELECT * from formulas WHERE id = :id")
    fun getItem(id: String): Flow<DbFormula>

    @Query("SELECT * from formulas ORDER BY name ASC")
    fun getAllItems(): Flow<List<DbFormula>>
}
