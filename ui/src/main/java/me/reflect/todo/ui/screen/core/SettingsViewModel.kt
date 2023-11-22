package me.reflect.todo.ui.screen.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.reflect.todo.domain.auth.usecases.GetUserUseCase
import me.reflect.todo.domain.auth.usecases.LogoutUseCase
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SettingsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {
    val user = getUserUseCase.invoke()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke()
        }
    }
}