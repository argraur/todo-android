package me.reflect.todo.common.token.di

import me.reflect.todo.common.token.TokenDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var tokenModule = module {
    single {
        TokenDataStore(androidContext())
    }
}