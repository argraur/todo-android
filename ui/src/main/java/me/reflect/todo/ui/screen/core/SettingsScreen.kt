package me.reflect.todo.ui.screen.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.reflect.todo.common.token.TokenDataStore
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(onNavigateToWelcome: () -> Unit, tokenDataStore: TokenDataStore = koinInject()) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Welcome to Settings screen!")

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    tokenDataStore.removeToken()
                    onNavigateToWelcome()
                }
            }) {
                Text(text = "Remove token")
            }
        }
    }
}
