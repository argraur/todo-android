package me.reflect.todo.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = koinViewModel(), onNavigateBack: () -> Unit, onNavigateToLogin: () -> Unit, onRegisterCompletion: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.state) {
        when (uiState.state) {
            RegisterState.REGISTERED -> onRegisterCompletion()
            RegisterState.ERROR ->
                Toast.makeText(context, "Не удалось выполнить вход", Toast.LENGTH_LONG).show()
            else -> {}
        }
    }

    Scaffold(
        topBar ={
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .imePadding()) {
            val (fieldsRef, buttonRef, indicatorRef) = createRefs()
            Column(
                modifier = Modifier
                    .constrainAs(fieldsRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 32.dp, start = 32.dp, end = 32.dp)
            ) {
                Text(text = "Давайте знакомиться!", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, fontSize = 32.sp)
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.firstName,
                    onValueChange = { viewModel.updateFirstName(it) },
                    placeholder = { Text("Имя") },
                    maxLines = 1,
                    enabled = uiState.state != RegisterState.LOADING,
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.lastName,
                    onValueChange = { viewModel.updateLastName(it) },
                    placeholder = { Text("Фамилия") },
                    maxLines = 1,
                    enabled = uiState.state != RegisterState.LOADING,
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = { Text("E-mail") },
                    maxLines = 1,
                    enabled = uiState.state != RegisterState.LOADING
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    placeholder = { Text("Пароль") },
                    maxLines = 1,
                    enabled = uiState.state != RegisterState.LOADING,
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            if (uiState.state != RegisterState.LOADING) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.login() },
                    modifier = Modifier
                        .constrainAs(buttonRef) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(24.dp),
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Зарегистрироваться"
                        )
                    },
                    text = { Text(text = "Зарегистрироваться") }
                )
            }

            if (uiState.state == RegisterState.LOADING) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .constrainAs(indicatorRef) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .padding(24.dp)
                )
            }
        }
    }
}