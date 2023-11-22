package me.reflect.todo.common.network.model.core

import kotlinx.serialization.Serializable

@Serializable
data class NetworkTask(
    var id: Long? = -1,
    var name: String? = "",
    var description: String? = "",
    var creatorId: Long? = -1,
    var assigneeId: List<Long>? = listOf(-1),
    var projectId: Long? = -1,
    var closed: Boolean? = false,
    var properties: NetworkTaskProperties? = NetworkTaskProperties("", "", "", ""),
    var createdAt: String? = ""
)