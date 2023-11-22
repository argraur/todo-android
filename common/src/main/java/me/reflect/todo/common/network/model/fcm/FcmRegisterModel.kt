package me.reflect.todo.common.network.model.fcm

import kotlinx.serialization.Serializable

@Serializable
data class FcmRegisterModel(
    val jwt: String,
    val token: String
)