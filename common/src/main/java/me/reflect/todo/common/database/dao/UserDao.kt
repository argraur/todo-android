package me.reflect.todo.common.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.reflect.todo.common.database.model.auth.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("DELETE from UserEntity")
    fun deleteUser()

    @Query("SELECT * from UserEntity LIMIT 1")
    fun getUser(): Flow<UserEntity>
}