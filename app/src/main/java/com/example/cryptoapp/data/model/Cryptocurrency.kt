package com.example.cryptoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity object to store cryptocurrency items. The only difference betweeen this entity and
 * [CryptocurrencyListing] is that this entity stores the URL of the cryptocurrency [logo]. The [id]
 * acts as a P-key (primary key) for the item, the [name] stores the full name of the cryptocurrency,
 * the [symbol] is the shortened form of the [name] (say, Bitcoin and BTC). The [percentage] is the delta
 * of the cryptocurrency compared to the value in the past 24 hrs.
 */
@Entity(tableName = "cryptocurrencies")
data class Cryptocurrency(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "logoUrl")
    val logo: String? = null,
    @ColumnInfo(name = "percentage")
    val percentage: Double? = null
)
