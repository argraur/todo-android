package me.reflect.todo.common.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ReflectFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.i("ReflectFirebaseMessagingService", "Message ID: ${message.messageId}")
        Log.i("ReflectFirebaseMessagingService", "Message: ${message.data}")
    }
}