package me.reflect.todo.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import me.reflect.todo.common.token.TokenDataStore
import me.reflect.todo.screens.BaseScreen
import me.reflect.todo.ui.screen.auth.LoginScreen
import me.reflect.todo.ui.screen.auth.RegisterScreen
import me.reflect.todo.ui.screen.auth.WelcomeScreen
import org.koin.compose.koinInject

@Composable
fun InitialNavHost(navController: NavHostController, tokenStorage: TokenDataStore = koinInject()) {
    val token = runBlocking { tokenStorage.readToken().firstOrNull() }
    var startDestination = "main"
    if (token.isNullOrEmpty())
        startDestination = "welcome"

    NavHost(navController = navController, startDestination = startDestination) {
        val navigateToLogin = { navController.navigate("login") }
        val navigateToRegister = { navController.navigate("register") }
        val navigateToMain = { navController.navigate("main") {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }}
        val navigateBack = { navController.popBackStack(); Unit }

        composable("welcome") {
            WelcomeScreen(
                onNavigateToLogin = navigateToLogin,
                onNavigateToRegister = navigateToRegister,
                onNavigateToMain = navigateToMain
            )
        }
        composable("login") {
            LoginScreen(
                onNavigateBack = navigateBack,
                onNavigateToRegister = navigateToRegister,
                onLoginCompletion = navigateToMain
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateBack = navigateBack,
                onNavigateToLogin = navigateToLogin,
                onRegisterCompletion = navigateToMain
            )
        }
        composable("main") {
            BaseScreen {
                navController.navigate("welcome") {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        }
    }
}