package me.reflect.todo.domain.auth.usecases

import me.reflect.todo.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class FetchUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        authRepository.refreshUser()
    }
}