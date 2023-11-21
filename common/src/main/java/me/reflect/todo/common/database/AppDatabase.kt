package me.reflect.todo.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.reflect.todo.common.database.dao.CoreDao
import me.reflect.todo.common.database.model.core.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coreDao(): CoreDao
}