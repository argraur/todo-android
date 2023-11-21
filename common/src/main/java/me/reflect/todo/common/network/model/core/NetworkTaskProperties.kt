package me.reflect.todo.common.network.model.core

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTaskProperties(
    var urgency: String,
    var importance: String,
    var type: String,
    var status: String
)
