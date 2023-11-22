package me.reflect.todo.domain.auth.usecases

import me.reflect.todo.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class GetUserUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.getUser()
}