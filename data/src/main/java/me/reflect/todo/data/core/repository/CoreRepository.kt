package me.reflect.todo.data.core.repository

import kotlinx.coroutines.flow.Flow
import me.reflect.todo.data.core.model.Task

interface CoreRepository {
    fun getMyTasksFlow(): Flow<List<Task>>
    suspend fun syncRepository()

    suspend fun addTask(task: Task): Boolean

    suspend fun deleteTask(task: Task): Boolean

    suspend fun getTaskById(id: String): Task
}