package me.reflect.todo.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.reflect.todo.common.database.converter.Converters
import me.reflect.todo.common.database.dao.CoreDao
import me.reflect.todo.common.database.model.core.TaskEntity

@Database(entities = [TaskEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coreDao(): CoreDao
}