package me.reflect.todo.common.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val error: String,
    val header: String = "",
    var description: String = ""
)