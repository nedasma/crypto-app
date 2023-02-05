package com.example.cryptoapp.data.model

/**
 * Wrapper data class for the [CryptocurrencyListing] due to parsed JSON containing the top "data" element.
 * Therefore, the [data] holds the list of [CryptocurrencyListing]s here only.
 */
data class CryptocurrencyListingResponse(
    val data: List<CryptocurrencyListing>
)
