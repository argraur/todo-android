package me.reflect.todo.domain.auth.usecases

import me.reflect.todo.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class LoginUseCase (
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String): Boolean {
        return authRepository.login(email, password)
    }
}