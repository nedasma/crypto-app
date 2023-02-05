package com.example.cryptoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.data.model.CryptoItem

/**
 * Data Access Object for the [CryptoItem]. Contains all Room database queries needed so that the
 * DB entity can be selected, updated, deleted etc.
 */
@Dao
interface CryptoItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: CryptoItem)

    @Delete
    suspend fun delete(item: CryptoItem)

    @Query("SELECT * FROM cryptoItems")
    suspend fun selectAll(): List<CryptoItem>
}