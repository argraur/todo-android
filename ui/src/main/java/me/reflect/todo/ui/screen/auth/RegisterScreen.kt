package me.reflect.todo.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RegisterScreen(onNavigateBack: () -> Unit, onNavigateToLogin: () -> Unit, onRegisterCompletion: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("This is a register screen!")
            FloatingActionButton(onClick = onNavigateBack) {
                Icon(Icons.Filled.ArrowBack, null)
            }
            Button(onClick = onNavigateToLogin) {
                Text(text = "Login instead")
            }
            Button(onClick = onRegisterCompletion) {
                Text(text = "Go to main")
            }
        }
    }
}