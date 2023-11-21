package me.reflect.todo.common.database.model.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
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