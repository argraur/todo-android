package me.reflect.todo.domain.repository

import kotlinx.coroutines.flow.Flow
import me.reflect.todo.domain.auth.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Boolean
    suspend fun register(email: String, password: String, firstName: String, lastName: String): Boolean
    suspend fun logout()
    suspend fun refreshUser(): Boolean
    fun getUser(): Flow<User>
}