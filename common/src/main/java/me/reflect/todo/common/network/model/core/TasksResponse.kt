package me.reflect.todo.common.network.model.core

import kotlinx.serialization.Serializable

@Serializable
data class TasksResponse(
    val tasks: List<NetworkTask>
)