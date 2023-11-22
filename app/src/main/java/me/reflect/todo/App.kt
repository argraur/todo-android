package me.reflect.todo

import android.app.Application
import me.reflect.todo.common.database.di.databaseModule
import me.reflect.todo.common.firebase.di.firebaseModule
import me.reflect.todo.common.network.di.networkModule
import me.reflect.todo.common.token.di.tokenModule
import me.reflect.todo.data.auth.di.dataAuthModule
import me.reflect.todo.data.core.di.dataCoreModule
import me.reflect.todo.domain.di.DomainModule
import me.reflect.todo.ui.di.UiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                networkModule,
                tokenModule,
                databaseModule,
                dataCoreModule,
                dataAuthModule,
                firebaseModule,
                DomainModule().module,
                UiModule().module
            )
        }
    }
}