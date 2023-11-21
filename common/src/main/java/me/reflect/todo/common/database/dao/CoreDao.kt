package me.reflect.todo.common.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.reflect.todo.common.database.model.core.TaskEntity

@Dao
interface CoreDao {
    @Query("SELECT * FROM TaskEntity")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id LIKE :id LIMIT 1")
    fun getTaskById(id: Long): Flow<TaskEntity>

    @Insert
    fun insertTask(task: TaskEntity)

    @Insert
    fun insertTasks(vararg tasks: TaskEntity)

    @Delete
    fun deleteTask(task: TaskEntity)
}