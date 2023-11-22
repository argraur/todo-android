package me.reflect.todo.common.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String
)