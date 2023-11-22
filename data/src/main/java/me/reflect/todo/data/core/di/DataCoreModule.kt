package me.reflect.todo.data.core.di

import me.reflect.todo.data.core.repository.CoreRepositoryImpl
import me.reflect.todo.domain.repository.CoreRepository
import org.koin.dsl.module

var dataCoreModule = module {
    single<me.reflect.todo.domain.repository.CoreRepository> { CoreRepositoryImpl(get(), get()) }
}