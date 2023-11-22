package me.reflect.todo.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.reflect.todo.domain.auth.usecases.LoginUseCase
import org.koin.android.annotation.KoinViewModel

enum class LoginState {
    NOT_LOGGED_IN,
    LOGGED_IN,
    LOADING,
    ERROR
}

data class LoginUiState(
    val state: LoginState = LoginState.NOT_LOGGED_IN,
    val email: String = "",
    val password: String = ""
)

@KoinViewModel
class LoginViewModel(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(state = LoginState.LOADING)
            }

            val result = loginUseCase.invoke(
                uiState.value.email,
                uiState.value.password
            )

            _uiState.update {
                if (result) {
                    it.copy(state = LoginState.LOGGED_IN)
                } else {
                    it.copy(state = LoginState.ERROR)
                }
            }
        }
    }

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }
}