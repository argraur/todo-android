package me.reflect.todo.common.network.model.core

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTask(
    var id: Long,
    var name: String,
    var description: String,
    var creatorId: Long,
    var assigneeId: Long,
    var projectId: Long,
    var closed: Boolean,
    var properties: NetworkTaskProperties
)