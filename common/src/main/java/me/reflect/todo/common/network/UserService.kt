package me.reflect.todo.common.network

import me.reflect.todo.common.network.model.auth.NetworkUser
import retrofit2.http.GET

interface UserService {
    @GET("/auth/user")
    suspend fun getUser(): NetworkUser
}