package me.reflect.todo.common.network.util

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.reflect.todo.common.token.TokenDataStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val tokenDataStore: TokenDataStore
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenDataStore.readToken().first() }
        Log.i("AuthInterceptor", "Intercepting request. Token = $token")
        return chain.proceed(authenticateRequest(chain.request(), token))
    }

    private fun authenticateRequest(request: Request, token: String): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
}