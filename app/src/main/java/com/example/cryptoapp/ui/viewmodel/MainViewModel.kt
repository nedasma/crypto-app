package com.example.cryptoapp.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.CryptoApp
import com.example.cryptoapp.data.model.CryptoItem
import com.example.cryptoapp.data.model.Cryptocurrency
import com.example.cryptoapp.data.model.CryptocurrencyListing
import com.example.cryptoapp.data.repository.MainRepository
import com.example.cryptoapp.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode

// region constants
const val PAGE_INCREMENTER = 50
private const val SCALE = 2
// endregion

/**
 * The VM of the application - all the business logic is contained in this class. The VM acts as a
 * middleman between the view (which should be as much deprived from the business logic as possible)
 * and the repository layer (which contains access points to the data layer). To access the repository,
 * the VM injects the [repository] instance in the constructor.
 */
class MainViewModel(
    app: Application,
    private val repository: MainRepository,
) : AndroidViewModel(app) {

    private val _data = MutableStateFlow<List<CryptoItem>>(listOf())
    val data: StateFlow<List<CryptoItem>> = _data

    private val _watchlistData = MutableStateFlow<List<CryptoItem>>(listOf())
    val watchlistData: StateFlow<List<CryptoItem>> = _watchlistData


    private val TAG = "MainViewModel"

    private var page = 1
    private var responseData: MutableList<CryptocurrencyListing>? = mutableListOf()
    private var persistedData: MutableList<Cryptocurrency>? = mutableListOf()

    // region crypto items list
    /**
     * Loads cryptocurrency data from the API. The method is called from the scrolling functionality,
     * therefore the [setIncrementer] flag can be set as "true" so that the starting index of the query
     * can be provided to the repository.
     */
    fun loadData(setIncrementer: Boolean = false) {

        val dataWithModifications = mutableListOf<CryptoItem>()

        viewModelScope.launch {
            if (setIncrementer) {
                page += PAGE_INCREMENTER
            }
            if (!hasInternetConnection()) {
                val response = repository.getAllCurrenciesFromDb()
                if (persistedData.isNullOrEmpty()) {
                    persistedData = response.toMutableList()
                } else {
                    persistedData?.addAll(response)
                }
                persistedData?.onEach {
                    dataWithModifications.add(
                        CryptoItem(
                            id = it.id,
                            name = it.name,
                            symbol = it.symbol,
                            percentage = it.percentage?.toFloat() ?: 0f
                        )
                    )
                }
            } else {
                when (val response = repository.getAllCurrencies(page)) {
                    is Result.Success -> {
                        val data = response.data?.data
                        if (responseData.isNullOrEmpty()) {
                            responseData = data?.toMutableList()
                        } else {
                            if (data != null) {
                                responseData?.addAll(data)
                            }
                        }
                        if (responseData != null) {
                            responseData?.onEach {
                                dataWithModifications.add(
                                    CryptoItem(
                                        id = it.id,
                                        name = it.name,
                                        symbol = it.symbol,
                                        percentage = it.quote.USD.percent_change_24h
                                            .toBigDecimal()
                                            .setScale(SCALE, RoundingMode.UP)
                                            .toFloat()
                                    )
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        Log.e(TAG, "database query returned an error")
                    }
                }
            }
            _data.value = dataWithModifications
            setData(dataWithModifications)
        }
    }

    /**
     * Sets [items] to the DB, which can later be queried directly through the repository without the
     * need of an API endpoint (e.g. no Internet connection)
     */
    private fun setData(items: List<CryptoItem>) {
        val currencyList = mutableListOf<Cryptocurrency>()
        viewModelScope.launch {
            if (items.isNotEmpty()) {
                items.onEach {
                    currencyList.add(
                        Cryptocurrency(
                            id = it.id,
                            name = it.name,
                            symbol = it.symbol,
                            percentage = it.percentage.toDouble()
                        )
                    )
                }
            }
            repository.addItems(currencyList)
        }
    }
    // endregion

    // region watchlist

    /**
     * Loads all watchlist items and provides them to the [_watchlistData] (note: underscore), which
     * is then exposed via the [watchlistData] to the view.
     */
    fun loadWatchlist() {
        viewModelScope.launch {
            val data = repository.getAllWatchlistItems()
            _watchlistData.value = data
        }
    }

    /**
     * Adds a single [item] to the watchlist.
     */
    fun addToWatchlist(item: CryptoItem) {
        viewModelScope.launch {
            repository.addToWatchlist(item)
        }
    }

    /**
     * Deletes a single [item] from the watchlist.
     */
    fun deleteFromWatchlist(item: CryptoItem) {
        viewModelScope.launch {
            repository.deleteFromWatchlist(item)
            loadWatchlist()
        }
    }
    // endregion

    /**
     * Helper method to check if the device has Internet connection. Returns true if there's connection,
     *  false otherwise
     */
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<CryptoApp>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: false as NetworkCapabilities
        return when {
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}
