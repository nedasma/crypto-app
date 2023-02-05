package com.example.cryptoapp.data.model

/**
 * Data class to wrap around the listings for a single cryptocurrency. The [id] is the unique identifier
 * for the element, the [name] is the full name of the currency, the [symbol] is the shortened form
 * of the [name] (Ethereum - ETH) and the [quote] is the trade data of the currency - the price,
 * changes in the past days, market caps etc. against various currencies (real or crypto).
 */
data class CryptocurrencyListing(
    val id: Int,
    val name: String,
    val symbol: String,
    val quote: Quote
)
