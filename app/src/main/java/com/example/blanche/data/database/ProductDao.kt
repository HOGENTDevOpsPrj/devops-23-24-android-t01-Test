package com.example.blanche.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: dbProduct)

    @Update
    suspend fun update(item: dbProduct)

    @Delete
    suspend fun delete(item: dbProduct)

    @Query("SELECT * from products WHERE id = :id")
    fun getItem(id: String): Flow<dbProduct>

    @Query("SELECT * from products ORDER BY name ASC")
    fun getAllItems(): Flow<List<dbProduct>>
}