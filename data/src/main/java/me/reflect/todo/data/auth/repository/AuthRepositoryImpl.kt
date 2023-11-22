package me.reflect.todo.data.auth.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext
import me.reflect.todo.common.database.dao.UserDao
import me.reflect.todo.common.network.AuthService
import me.reflect.todo.common.network.UserService
import me.reflect.todo.common.network.model.auth.LoginModel
import me.reflect.todo.common.network.model.auth.RegisterModel
import me.reflect.todo.common.token.TokenDataStore
import me.reflect.todo.data.auth.model.User
import me.reflect.todo.data.auth.model.asDataModel
import me.reflect.todo.data.auth.model.asEntity

class AuthRepositoryImpl (
    private val authService: AuthService,
    private val userService: UserService,
    private val userDao: UserDao,
    private val tokenDataStore: TokenDataStore,
) : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val token = authService.login(LoginModel(email, password))
                if (token.token.isNotEmpty()) {
                    tokenDataStore.writeToken(token.token)
                    return@withContext true
                }
                false
            } catch (e: Exception) {
                Log.e(this::class.simpleName, "Failed to login user", e)
                false
            }
        }
    }

    override suspend fun register(email: String, password: String, firstName: String, lastName: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val token =
                    authService.register(RegisterModel(email, password, firstName, lastName))
                if (token.token.isNotEmpty()) {
                    tokenDataStore.writeToken(token.token)
                    return@withContext true
                }
                false
            } catch (e: Exception) {
                Log.e(this::class.simpleName, "Failed to register user", e)
                false
            }
        }
    }

    override suspend fun logout() {
        withContext(Dispatchers.IO) {
            userDao.deleteUser()
            tokenDataStore.removeToken()
        }
    }

    override suspend fun refreshUser(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val user = userService.getUser()
                userDao.deleteUser()
                userDao.insertUser(user.asEntity())
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun getUser(): Flow<User> {
        return try {
            val userFlow = userDao.getUser().mapNotNull {
                try {
                    it.asDataModel()
                } catch (e: Exception) {
                    User(-1, "", "", "", false, "")
                }
            }
            userFlow
        } catch (e: Exception) {
            throw IllegalStateException("No user in database!")
        }
    }
}