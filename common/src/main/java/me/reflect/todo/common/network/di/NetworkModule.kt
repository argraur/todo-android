package me.reflect.todo.common.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import me.reflect.todo.common.network.AuthService
import me.reflect.todo.common.network.CoreService
import me.reflect.todo.common.network.util.AuthInterceptor
import me.reflect.todo.common.network.util.Constants
import me.reflect.todo.common.token.TokenDataStore
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

var networkModule = module {
    single {
        AuthInterceptor(get())
    }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .client(get<OkHttpClient>())
            .baseUrl(Constants.CORE_URL)
            .build()
            .create(CoreService::class.java)
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json")!!))
            .baseUrl(Constants.CORE_URL)
            .build()
            .create(AuthService::class.java)
    }
}