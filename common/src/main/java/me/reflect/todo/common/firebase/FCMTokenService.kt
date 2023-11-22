package me.reflect.todo.common.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.reflect.todo.common.network.FCMService
import me.reflect.todo.common.network.model.fcm.FcmRegisterModel
import me.reflect.todo.common.token.TokenDataStore
import org.koin.core.annotation.Single

@Single
class FCMTokenService(private val fcmService: FCMService, private val tokenDataStore: TokenDataStore) {
    fun onAuthTokenReady() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(this::class.simpleName, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result

            CoroutineScope(Dispatchers.IO + Job()).launch {
                val jwt = tokenDataStore.readToken().first()
                fcmService.registerFcmToken(FcmRegisterModel(jwt, token))
            }

            val msg = "Token: $token"
            Log.d(this::class.simpleName, msg)
        }
    }
}