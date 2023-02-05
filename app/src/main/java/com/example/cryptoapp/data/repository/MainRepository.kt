package com.example.cryptoapp.data.repository

import com.example.cryptoapp.data.Api
import com.example.cryptoapp.data.AppDatabase
import com.example.cryptoapp.data.model.CryptoItem
import com.example.cryptoapp.data.model.Cryptocurrency
import com.example.cryptoapp.data.model.CryptocurrencyListingResponse
import com.example.cryptoapp.utils.Result

private const val QUERY_LIMIT = 50

/**
 * Main application repository, acting as a middleman between the VM (business logic) and the data
 * layer (data entities, DAOs, DTOs etc.). Injects an [api] for requests coming from the Web API
 * endpoints and the [db] for queries in the database layer.
 */
class MainRepository(
    private val api : Api,
    private val db: AppDatabase
) {

    /**
     * Gets all cryptocurrencies from the Web API endpoint with the given starting index ([page]).
     * Returns a [Result] containing the [CryptocurrencyListingResponse].
     */
    suspend fun getAllCurrencies(page: Int): Result<CryptocurrencyListingResponse> {
        val response = api.getAllCurrencies(page = page, limit = QUERY_LIMIT)
        val result = response.body()
        return if (response.isSuccessful && result != null) {
            Result.Success(result)
        } else {
            Result.Error(response.message())
        }
    }
    /**
     * Gets all cryptocurrencies from the database.
     * Returns a [Result] containing the list of [Cryptocurrency].
     */
    suspend fun getAllCurrenciesFromDb() = db.getCryptocurrencyDao().selectAll()

    /**
     * Adds all [items] to the database. As the DB query is an _UPSERT_, the already existing items in
     * the table are updated with the _ON DUPLICATE KEY UPDATE_ SQL part of the query.
     */
    suspend fun addItems(items: List<Cryptocurrency>) = db.getCryptocurrencyDao().upsertAll(items)

    /**
     * Adds a [watchlistItem] to the [CryptoItem] database table. If the item exists, it'll be updated
     * with new values instead.
     */
    suspend fun addToWatchlist(watchlistItem: CryptoItem) = db.getCryptoItemDao().upsert(watchlistItem)

    /**
     * Gets all watchlist items from the database. Returns a list of [CryptoItem]s.
     */
    suspend fun getAllWatchlistItems() = db.getCryptoItemDao().selectAll()

    /**
     * Deletes a [watchlistItem] from the [CryptoItem] database table.
     */
    suspend fun deleteFromWatchlist(watchlistItem: CryptoItem) = db.getCryptoItemDao().delete(watchlistItem)


    /**
     * Gets a cryptocurrency from the Web API endpoint with the given [id]. Returns a [Result]
     * containing the [Cryptocurrency].
     */
    // TODO: can be enabled once the custom Moshi converter is written for the provided JSON
//    suspend fun getCurrencyByName(id: String): Result<Cryptocurrency> {
//        val response = api.getCurrencyById(id)
//        val result = response.body()
//        Log.e("repo", "$result, $response")
//        return if (response.isSuccessful && result != null) {
//            Result.Success(result)
//        } else {
//            Result.Error(response.message())
//        }
//    }
}