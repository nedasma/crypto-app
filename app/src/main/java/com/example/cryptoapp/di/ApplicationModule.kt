package com.example.cryptoapp.di

import com.example.cryptoapp.data.Api
import com.example.cryptoapp.data.repository.MainRepository
import com.example.cryptoapp.ui.viewmodel.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// region constants
private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/"
private const val SANDBOX_BASE_URL = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/"
// endregion

val applicationModule = module {
    single {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        val okHttpBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor)

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpBuilder.build())
            .build()
            .create(Api::class.java)
    }
    single {
        MainRepository(get(), get())
    }

    viewModel {
        MainViewModel(get(), get())
    }
}