package me.reflect.todo.domain.core.usecases

import me.reflect.todo.data.auth.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.logout()
    }
}