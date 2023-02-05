package com.example.cryptoapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptoapp.data.dao.CryptoItemDao
import com.example.cryptoapp.data.dao.CryptocurrencyDao
import com.example.cryptoapp.data.model.CryptoItem
import com.example.cryptoapp.data.model.Cryptocurrency

/**
 * Room database class which allows accessing of data access objects (DAOs) for repositories and also
 * provides an invokation/creation of the database method which can be injected in the main app as a
 * singleton instance.
 */
@Database(entities = [Cryptocurrency::class, CryptoItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Abstract method allowing access to the [CryptocurrencyDao] for repositories. Implemented under
     * the hood by Room.
     */
    abstract fun getCryptocurrencyDao(): CryptocurrencyDao
    /**
     * Abstract method allowing access to the [CryptoItemDao] for repositories. Implemented under
     * the hood by Room.
     */
    abstract fun getCryptoItemDao(): CryptoItemDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val lock = Any()

        /**
         * Creates a new database instance with the given [context] or returns the existing [AppDatabase]
         * instance.
         */
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "database.db"
            ).build()
    }
}
