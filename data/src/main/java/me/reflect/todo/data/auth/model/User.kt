package me.reflect.todo.data.auth.model

import me.reflect.todo.common.database.model.core.UserEntity
import me.reflect.todo.common.network.model.auth.NetworkUser

data class User(
    val userId: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val enable: Boolean,
    val createdAt: String
)

fun NetworkUser.asEntity() = UserEntity(
    userId = userId,
    email = email,
    firstName = firstName,
    lastName = lastName,
    enable = enable,
    createdAt = createdAt
)

fun UserEntity.asDataModel() = User(
    userId = userId,
    email = email,
    firstName = firstName,
    lastName = lastName,
    enable = enable,
    createdAt = createdAt
)