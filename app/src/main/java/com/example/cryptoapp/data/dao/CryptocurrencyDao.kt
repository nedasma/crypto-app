package com.example.cryptoapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.data.model.Cryptocurrency

/**
 * Data Access Object for the [Cryptocurrency]. Contains all Room database queries needed so that the
 * DB entity can be selected, updated, deleted etc.
 */
@Dao
interface CryptocurrencyDao {

    @Query("SELECT * FROM cryptocurrencies")
    suspend fun selectAll(): List<Cryptocurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(currencies: List<Cryptocurrency>)

    @Delete
    suspend fun delete(user: Cryptocurrency)

}