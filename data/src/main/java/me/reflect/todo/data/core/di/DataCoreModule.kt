package me.reflect.todo.data.core.di

import me.reflect.todo.data.core.repository.CoreRepositoryImpl
import me.reflect.todo.data.core.repository.CoreRepository
import org.koin.dsl.module

var dataCoreModule = module {
    single<CoreRepository> { CoreRepositoryImpl(get(), get()) }
}