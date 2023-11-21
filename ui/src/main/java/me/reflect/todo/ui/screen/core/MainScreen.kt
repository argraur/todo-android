package me.reflect.todo.ui.screen.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.reflect.todo.common.theme.ReDoTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier.fillMaxSize(), viewModel: MainViewModel = koinViewModel()) {
    val tasks by viewModel.tasks.collectAsState(initial = listOf())
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(name)
            Text("Tasks size: ${tasks.size}")
            LazyRow {
                items(tasks) {
                    Text(it.name)
                }
            }
            Button(onClick = { viewModel.refreshRepository() }) {
                Text("Refresh")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReDoTheme {
        MainScreen("Android")
    }
}