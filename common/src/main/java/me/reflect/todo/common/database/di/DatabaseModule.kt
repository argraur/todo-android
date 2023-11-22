package me.reflect.todo.common.database.di

import androidx.room.Room
import me.reflect.todo.common.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

var databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().coreDao() }
    single { get<AppDatabase>().userDao() }
}