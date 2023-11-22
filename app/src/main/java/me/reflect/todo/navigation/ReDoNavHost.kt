package me.reflect.todo.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.reflect.todo.R
import me.reflect.todo.ui.screen.core.ToDoScreen
import me.reflect.todo.ui.screen.core.SettingsScreen
import me.reflect.todo.ui.screen.core.NotificationsScreen

sealed class ReDoScreen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    data object Main : ReDoScreen("main", R.string.main, Icons.Default.DateRange)
    data object Settings : ReDoScreen("settings", R.string.settings, Icons.Default.Settings)
    data object Notifications : ReDoScreen("notifications", R.string.fcm, Icons.Default.Notifications)
}

@Composable
fun ReDoNavHost(navController: NavHostController, padding: PaddingValues, onNavigateToWelcome: () -> Unit) {
    NavHost(navController = navController, startDestination = "main", modifier = Modifier.padding(padding)) {
        composable(ReDoScreen.Main.route) { ToDoScreen() }
        composable(ReDoScreen.Settings.route) { SettingsScreen(onNavigateToWelcome) }
        composable(ReDoScreen.Notifications.route) { NotificationsScreen() }
    }
}