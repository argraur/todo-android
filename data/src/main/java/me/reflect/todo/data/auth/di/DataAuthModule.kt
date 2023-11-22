package me.reflect.todo.data.auth.di

import me.reflect.todo.data.auth.repository.AuthRepository
import me.reflect.todo.data.auth.repository.AuthRepositoryImpl
import org.koin.dsl.module


val dataAuthModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get(), get()) }
}