package com.example.cryptoapp.data.model

/**
 * Wrapper data class to store quotes for the cryptocurrencies. Currently only the quote against
 * the [USD] (US Dollar) is supported but can be easily extended to other currencies (real or crypto)
 * as well.
 */
data class Quote(
    val USD: DollarConvertedValue,
)

/**
 * Data class containing the conversion values of the [Quote]. As an example, the price of Bitcoin
 * is 1 BTC = $9283.92, so the [price] will be $9283.92. In the past day, the value of BTC has jumped
 * by 51%, therefore the [percent_change_24h] will be stored in a double as 0.51.
 */
data class DollarConvertedValue(
    val price: Double,
    val percent_change_24h: Double
)
