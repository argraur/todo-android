package me.reflect.todo.common.firebase.di

import me.reflect.todo.common.firebase.FCMTokenService
import org.koin.dsl.module

val firebaseModule = module {
    single { FCMTokenService(get(), get()) }
}