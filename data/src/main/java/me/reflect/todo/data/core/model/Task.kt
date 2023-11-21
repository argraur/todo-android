package me.reflect.todo.data.core.model

import me.reflect.todo.common.database.model.core.TaskEntity
import me.reflect.todo.common.network.model.core.NetworkTask

data class Task(
    var id: Long,
    var name: String,
    var description: String,
    var creatorId: Long,
    var assigneeId: Long,
    var projectId: Long,
    var closed: Boolean,
    var urgency: String,
    var importance: String,
    var type: String,
    var status: String
)

fun TaskEntity.asDataModel() = Task(
    id = id,
    name = name,
    description = description,
    creatorId = creatorId,
    assigneeId = assigneeId,
    projectId = projectId,
    closed = closed,
    urgency = urgency,
    importance = importance,
    type = type,
    status = status
)

fun NetworkTask.asEntity() = TaskEntity(
    id = id,
    name = name,
    description = description,
    creatorId = creatorId,
    assigneeId = assigneeId,
    projectId = projectId,
    closed = closed,
    urgency = properties.urgency,
    importance = properties.importance,
    type = properties.type,
    status = properties.status
)