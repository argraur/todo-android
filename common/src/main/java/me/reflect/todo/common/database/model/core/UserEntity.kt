package me.reflect.todo.common.database.model.core

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    val userId: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val enable: Boolean,
    val createdAt: String
)