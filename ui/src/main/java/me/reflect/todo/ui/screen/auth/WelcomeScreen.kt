package me.reflect.todo.ui.screen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import me.reflect.todo.common.token.TokenDataStore
import org.koin.compose.koinInject

@Composable
fun WelcomeScreen(tokenDataStore: TokenDataStore = koinInject(), onNavigateToLogin: () -> Unit, onNavigateToRegister: () -> Unit, onNavigateToMain: () -> Unit) {
    Scaffold {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(24.dp)) {
            val (welcomeRef, buttonsRef) = createRefs()
            Column(modifier = Modifier
                .fillMaxWidth()
                .constrainAs(welcomeRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 120.dp)
            ) {
                Text(text = "Добро пожаловать в ReDo!", fontSize = 24.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Для доступа к функционалу приложения необходимо войти в аккаунт", fontSize = 14.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "\uD83D\uDC4B", fontSize = 96.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }

            Column(
                modifier = Modifier.constrainAs(buttonsRef) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { onNavigateToLogin() }
                ) {
                    Text(text = "Войти")
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { onNavigateToRegister() }) {
                    Text(text = "Ещё не с нами?")
                }
            }

        }
    }
}