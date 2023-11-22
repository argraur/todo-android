package me.reflect.todo.ui.screen.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onNavigateToWelcome: () -> Unit, viewModel: SettingsViewModel = koinViewModel()) {
    val user by viewModel.user.collectAsState(initial = null)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(0.dp),
                title = {
                Text(
                    text = "Настройки",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            })
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (user != null) {
                val (name, email, logout) = createRefs()
                Column(modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }.padding(24.dp)) {
                    Text(text = user!!.firstName + " " + user!!.lastName, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Medium)
                    Text(text = user!!.email, style = MaterialTheme.typography.bodySmall, fontFamily = FontFamily.Monospace)
                }
                TextButton(
                    modifier = Modifier.constrainAs(logout) {
                        top.linkTo(name.bottom)
                        end.linkTo(parent.end)
                    }.padding(horizontal = 24.dp),
                    onClick = {
                        viewModel.logout()
                        onNavigateToWelcome()
                    }
                ) {
                    Text(text = "Выйти из аккаунта")
                }
            }
        }

    }
}
