package me.reflect.todo.ui.screen.auth

import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
fun LoginScreen(viewModel: LoginViewModel = koinViewModel(), onNavigateBack: () -> Unit, onNavigateToRegister: () -> Unit, onLoginCompletion: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.state) {
        when (uiState.state) {
            LoginState.LOGGED_IN -> onLoginCompletion()
            LoginState.ERROR ->
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
                    .padding(top = 120.dp, start = 32.dp, end = 32.dp)
            ) {
                Text(text = "С возвращением!", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, fontSize = 32.sp)
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = { Text("E-mail") },
                    maxLines = 1,
                    enabled = uiState.state != LoginState.LOADING
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    placeholder = { Text("Пароль") },
                    maxLines = 1,
                    enabled = uiState.state != LoginState.LOADING,
                    visualTransformation = PasswordVisualTransformation()
                )
            }

            if (uiState.state != LoginState.LOADING) {
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
                            contentDescription = "Login"
                        )
                    },
                    text = { Text(text = "Войти") }
                )
            }

            if (uiState.state == LoginState.LOADING) {
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(indicatorRef) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }.padding(24.dp)
                )
            }
        }
    }
}