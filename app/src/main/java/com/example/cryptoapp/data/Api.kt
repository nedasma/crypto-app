package com.example.cryptoapp.data

import com.example.cryptoapp.data.model.Cryptocurrency
import com.example.cryptoapp.data.model.CryptocurrencyListingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// region constants
private const val API_KEY = "98d3fb39-9f13-433a-900e-10a2ea0d0ba3"
private const val SANDBOX_API_KEY = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c"
// endregion

/**
 * An interface which acts as an access point with the Web API endpoints. The interface can be injected
 * into repositories, and repositories can directly call interface methods to get/post data provided.
 */
interface Api {

    /**
     * GETs all cryptocurrencies from the Web API endpoint. Required headers are as follows:
     * - acceptance header: accepts JSON format
     * - authorisation API key
     *
     * Query can be provided with the optional [page] parameter to denote from which index the query
     * should start (almost like paging but instead of pages being "1, 2, 3" there's a starting index
     * i.e. "25, 47" etc.)
     *
     * The [limit] parameter acts in the same fashion as the SQL _LIMIT_ condition.
     *
     * The method returns a [Response] containing the data parsed in the [CryptocurrencyListingResponse].
     */
    @Headers(
        value = [
            "Accept: application/json",
            "X-CMC_PRO_API_KEY: $API_KEY"
        ]
    )
    @GET("listings/latest")
    suspend fun getAllCurrencies(
        @Query("start")
        page: Int? = null,
        @Query("limit")
        limit: Int?
    ): Response<CryptocurrencyListingResponse>

    /**
     * GETs the cryptocurrency from the Web API endpoint by given cryptocurrency [id].
     *
     * Required headers are as follows:
     * - acceptance header: accepts JSON format
     * - authorisation API key
     *
     * The method returns a [Response] containing the data parsed in the [Cryptocurrency] data class.
     */
    @Headers(
        value = [
            "Accept: application/json",
            "X-CMC_PRO_API_KEY: $API_KEY"
        ]
    )
    @GET("info")
    suspend fun getCurrencyById(
        @Query("symbol")
        id: String
    ): Response<Cryptocurrency>
}