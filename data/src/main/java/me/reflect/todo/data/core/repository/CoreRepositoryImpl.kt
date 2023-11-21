package me.reflect.todo.data.core.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.reflect.todo.common.database.dao.CoreDao
import me.reflect.todo.common.database.model.core.TaskEntity
import me.reflect.todo.common.network.CoreService
import me.reflect.todo.common.network.model.core.NetworkTask
import me.reflect.todo.data.core.model.Task
import me.reflect.todo.data.core.model.asDataModel
import me.reflect.todo.data.core.model.asEntity

class CoreRepositoryImpl(
    private val coreService: CoreService,
    private val coreDao: CoreDao
): CoreRepository {
    override fun getMyTasksFlow(): Flow<List<Task>> =
        coreDao.getAllTasks()
            .map { it.map(TaskEntity::asDataModel) }

    override suspend fun syncRepository() {
        try {
            val tasks = coreService.getMyTasks()
            coreDao.insertTasks(*(tasks.tasks.map(NetworkTask::asEntity)).toTypedArray())
        } catch (e: Exception) {
            Log.e(this::class.simpleName, "Error occurred while synchronizing: ${e.stackTraceToString()}")
        }
    }
}