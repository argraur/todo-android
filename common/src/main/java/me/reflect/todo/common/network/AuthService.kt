package me.reflect.todo.common.network

import me.reflect.todo.common.network.model.auth.LoginModel
import me.reflect.todo.common.network.model.auth.RegisterModel
import me.reflect.todo.common.network.model.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    suspend fun login(@Body loginModel: LoginModel): TokenResponse

    @POST("/auth/signup")
    suspend fun register(@Body registerModel: RegisterModel): TokenResponse
}