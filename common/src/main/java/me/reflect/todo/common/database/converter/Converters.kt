package me.reflect.todo.common.database.converter

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromLongList(value: List<Long>): String {
        return value.let { value.joinToString(",") }
    }

    @TypeConverter
    fun stringToLongList(value: String): List<Long> {
        return value.let { if (value.isEmpty()) listOf() else value.split(",").map { it.toLong() } }
    }
}