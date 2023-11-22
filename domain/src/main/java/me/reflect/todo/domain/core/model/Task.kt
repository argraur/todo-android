package me.reflect.todo.domain.core.model

import me.reflect.todo.common.database.model.core.TaskEntity
import me.reflect.todo.common.network.model.core.NetworkTask
import me.reflect.todo.common.network.model.core.NetworkTaskProperties
import me.reflect.todo.domain.core.model.enums.Importance
import me.reflect.todo.domain.core.model.enums.Status
import me.reflect.todo.domain.core.model.enums.Type
import me.reflect.todo.domain.core.model.enums.Urgency

data class Task(
    var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var creatorId: Long = 1,
    var assigneeId: List<Long> = listOf(1),
    var projectId: Long = -1,
    var closed: Boolean = false,
    var urgency: Urgency = Urgency.NOT_URGENT,
    var importance: Importance = Importance.NOT_IMPORTANT,
    var type: Type = Type.NEW,
    var status: Status = Status.OPENED,
    var createdAt: String = ""
)

fun Task.asEntity() = TaskEntity(
    id = id,
    name = name,
    description = description,
    creatorId = creatorId,
    assigneeId = assigneeId,
    projectId = projectId,
    closed = closed,
    urgency = urgency.toString(),
    importance = importance.toString(),
    type = type.toString(),
    status = status.toString(),
    createdAt = createdAt
)

fun Task.asNetwork() = NetworkTask(
    name = name,
    description = description,
    creatorId = creatorId,
    assigneeId = assigneeId,
    projectId = projectId,
    closed = closed,
    properties = NetworkTaskProperties(
        urgency = urgency.toString(),
        importance = importance.toString(),
        type = type.toString(),
        status = status.toString()
    )
)

fun TaskEntity.asDataModel() = Task(
    id = id,
    name = name,
    description = description,
    creatorId = creatorId,
    assigneeId = assigneeId,
    projectId = projectId,
    closed = closed,
    urgency = Urgency.valueOf(urgency),
    importance = Importance.valueOf(importance),
    type = Type.valueOf(type),
    status = Status.valueOf(status),
    createdAt = createdAt
)

fun NetworkTask.asEntity() = TaskEntity(
    id = id!!,
    name = name ?: "",
    description = description ?: "",
    creatorId = creatorId ?: 0,
    assigneeId = assigneeId ?: listOf(),
    projectId = projectId ?: 0,
    closed = closed ?: false,
    urgency = properties?.urgency ?: Urgency.NOT_URGENT.toString(),
    importance = properties?.importance ?: Importance.NOT_IMPORTANT.toString(),
    type = properties?.type ?: Type.NEW.toString(),
    status = properties?.status ?: Status.OPENED.toString(),
    createdAt = createdAt ?: ""
)