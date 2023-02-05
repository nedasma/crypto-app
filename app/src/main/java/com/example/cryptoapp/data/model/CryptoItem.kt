package com.example.cryptoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity object to store cryptocurrency items. The [id] acts as a P-key (primary key) for
 * the item, the [name] stores the full name of the cryptocurrency, the [symbol] is the shortened form
 * of the [name] (say, Ethereum and ETH) and [percentage] is the delta of the current value compared
 * with the value from the last 24 hrs against the US Dollar (as for now).
 */
@Entity(tableName = "cryptoItems")
data class CryptoItem(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "symbol")
    val symbol: String,
    @ColumnInfo(name = "percentage")
    val percentage: Float
)
