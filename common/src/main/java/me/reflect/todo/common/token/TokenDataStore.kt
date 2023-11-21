package me.reflect.todo.common.token

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.reflect.todo.common.token.util.Constants

class TokenDataStore(
    private val context: Context
) {
    private val tokenKey = stringPreferencesKey(Constants.tokenKey)

    private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(Constants.tokenDataStoreName)

    fun readToken(): Flow<String> {
        return context.dataStore.data.map {
            val token = it[tokenKey]
            Log.i("TokenDataStore", token ?: "")
            it[tokenKey] ?: ""
        }
    }

    suspend fun writeToken(token: String) {
        context.dataStore.edit {
            it[tokenKey] = token
        }
    }

    suspend fun removeToken() {
        context.dataStore.edit {
            it[tokenKey] = ""
        }
    }
}