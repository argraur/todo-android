package me.reflect.todo.common.network.model.core

import kotlinx.serialization.Serializable

@Serializable
data class NetworkProject(
    var id: String,
    var members: List<Long>,
    var properties: NetworkProjectProperties
)