package me.reflect.todo.data.core.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import me.reflect.todo.common.database.dao.CoreDao
import me.reflect.todo.common.database.model.core.TaskEntity
import me.reflect.todo.common.network.CoreService
import me.reflect.todo.common.network.model.core.NetworkTask
import me.reflect.todo.data.core.model.Task
import me.reflect.todo.data.core.model.asDataModel
import me.reflect.todo.data.core.model.asEntity
import me.reflect.todo.data.core.model.asNetwork

class CoreRepositoryImpl(
    private val coreService: CoreService,
    private val coreDao: CoreDao
): CoreRepository {
    override fun getMyTasksFlow(): Flow<List<Task>> =
        coreDao.getAllTasks()
            .map { it.map(TaskEntity::asDataModel) }

    override suspend fun syncRepository() {
        withContext(Dispatchers.IO) {
            try {
                val tasks = coreService.getMyTasks()
                Log.i("CoreRepository", "Received tasks: $tasks")
                coreDao.deleteAllTasks()
                coreDao.insertTasks(*(tasks.map(NetworkTask::asEntity)).toTypedArray())
            } catch (e: Exception) {
                Log.e(
                    this::class.simpleName,
                    "Error occurred while synchronizing: ${e.stackTraceToString()}"
                )
            }
        }
    }

    override suspend fun addTask(task: Task): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val result = coreService.addTask(task.asNetwork())
                coreDao.insertTask(result.asEntity())
                return@withContext true
            } catch (e: Exception) {
                Log.e(this::class.simpleName, "Error occurred while synchronizing", e)
                return@withContext false
            }
        }
    }

    override suspend fun deleteTask(task: Task): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = coreService.deleteTaskById(task.id)
                if (response.isSuccessful) {
                    coreDao.deleteTask(task.asEntity())
                    return@withContext true
                }
                return@withContext false
            } catch (e: Exception) {
                Log.e(this::class.simpleName, "Error occurred while removing task with id = ${task.id}", e)
                return@withContext false
            }
        }
    }

    override suspend fun getTaskById(id: String): Task {
        return withContext(Dispatchers.IO) {
            return@withContext coreDao.getTaskById(id.toLong())
        }.first().asDataModel()
    }
}