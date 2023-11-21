package me.reflect.todo.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.reflect.todo.R
import me.reflect.todo.ui.screen.core.MainScreen
import me.reflect.todo.ui.screen.core.SettingsScreen

sealed class ReDoScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object Main : ReDoScreen("main", R.string.main, Icons.Default.Home)
    data object Settings : ReDoScreen("settings", R.string.settings, Icons.Default.Settings)
}

@Composable
fun ReDoNavHost(navController: NavHostController, padding: PaddingValues, onNavigateToWelcome: () -> Unit) {
    NavHost(navController = navController, startDestination = "main", modifier = Modifier.padding(padding)) {
        composable(ReDoScreen.Main.route) { MainScreen(name = "Android") }
        composable(ReDoScreen.Settings.route) { SettingsScreen(onNavigateToWelcome) }
    }
}