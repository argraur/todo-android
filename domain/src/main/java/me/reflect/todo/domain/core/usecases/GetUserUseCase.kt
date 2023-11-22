package me.reflect.todo.domain.core.usecases

import kotlinx.coroutines.flow.Flow
import me.reflect.todo.data.auth.model.User
import me.reflect.todo.data.auth.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class GetUserUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User> {
        return authRepository.getUser()
    }
}