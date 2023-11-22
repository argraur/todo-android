package me.reflect.todo.domain.auth.usecases

import me.reflect.todo.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class RegisterUseCase (
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String, firstName: String, lastName: String): Boolean {
        return authRepository.register(email, password, firstName, lastName)
    }
}