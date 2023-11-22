package me.reflect.todo.ui.screen.core

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.messaging.FirebaseMessaging

@Composable
fun NotificationsScreen() {
    val context = LocalContext.current
    var token by remember { mutableStateOf("") }
    var isTokenProvided by remember { mutableStateOf(false) }

    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.e("FCMTestScreen", "Fetching FCM registration token failed", task.exception)
            return@addOnCompleteListener
        }

        token = task.result
        isTokenProvided = true

        val msg = "Token: $token"
        Log.d("FCMTestScreen", msg)
    }

    LaunchedEffect(isTokenProvided) {
        if (isTokenProvided) {
            Toast.makeText(context, "Token was provided!", Toast.LENGTH_LONG).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(text = "Is token provided: $isTokenProvided")
            Spacer(modifier = Modifier.height(12.dp))
            if (isTokenProvided) {
                Text(text = "Token: $token")
            }
        }
    }
}