package me.reflect.todo.common.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)
