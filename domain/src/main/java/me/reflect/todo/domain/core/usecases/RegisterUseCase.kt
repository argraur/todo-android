package me.reflect.todo.domain.core.usecases

import me.reflect.todo.data.auth.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class RegisterUseCase (
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email: String, password: String, firstName: String, lastName: String): Boolean {
        return authRepository.register(email, password, firstName, lastName)
    }
}