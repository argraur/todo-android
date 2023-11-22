package me.reflect.todo.common.network.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class NetworkUser(
    val userId: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val enable: Boolean,
    val createdAt: String
)