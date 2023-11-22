package me.reflect.todo.common.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import me.reflect.todo.common.network.AuthService
import me.reflect.todo.common.network.CoreService
import me.reflect.todo.common.network.util.AuthInterceptor
import me.reflect.todo.common.network.util.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

var networkModule = module {
    single {
        AuthInterceptor(get())
    }

    factory {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(get<AuthInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .client(get<OkHttpClient>())
            .baseUrl(Constants.CORE_URL)
            .build()
            .create(CoreService::class.java)
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaTypeOrNull()!!))
            .baseUrl(Constants.CORE_URL)
            .build()
            .create(AuthService::class.java)
    }
}