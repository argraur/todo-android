package me.reflect.todo.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.reflect.todo.domain.core.usecases.RegisterUseCase
import org.koin.android.annotation.KoinViewModel

enum class RegisterState {
    NOT_REGISTERED,
    REGISTERED,
    LOADING,
    ERROR
}

data class RegisterUiState(
    val state: RegisterState = RegisterState.NOT_REGISTERED,
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = ""
)

@KoinViewModel
class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(state = RegisterState.LOADING)
            }

            val result = registerUseCase.invoke(
                uiState.value.email,
                uiState.value.password,
                uiState.value.firstName,
                uiState.value.lastName
            )

            _uiState.update {
                if (result) {
                    it.copy(state = RegisterState.REGISTERED)
                } else {
                    it.copy(state = RegisterState.ERROR)
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

    fun updateFirstName(firstName: String) {
        _uiState.update {
            it.copy(firstName = firstName)
        }
    }

    fun updateLastName(lastName: String) {
        _uiState.update {
            it.copy(lastName = lastName)
        }
    }
}