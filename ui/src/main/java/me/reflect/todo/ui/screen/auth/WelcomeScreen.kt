package me.reflect.todo.ui.screen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.reflect.todo.common.token.TokenDataStore
import org.koin.compose.koinInject

@Composable
fun WelcomeScreen(tokenDataStore: TokenDataStore = koinInject(), onNavigateToLogin: () -> Unit, onNavigateToRegister: () -> Unit, onNavigateToMain: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to ReflectMe To-Do!", fontSize = 24.sp)

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    tokenDataStore.writeToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmdyYXVyQGdtYWlsLmNvbSIsInVzZXJJZCI6MSwiZW5hYmxlIjpmYWxzZSwiZXhwIjoxNzAxMjA1NTAxfQ.FoNlYGdqGq2bM83glDf25L3zImZ2smuvAV8MjOgA1n8")
                    onNavigateToMain()
                }
            }) {
                Text(text = "Test token provision")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { onNavigateToLogin() }) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(onClick = { onNavigateToRegister() }) {
                Text(text = "Register")
            }
        }
    }
}