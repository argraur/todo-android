package me.reflect.todo.ui.screen.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import me.reflect.todo.data.core.model.Task
import me.reflect.todo.domain.core.usecases.GetTasksUseCase
import me.reflect.todo.domain.core.usecases.RefreshRepositoryUseCase
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val getTasksUseCase: GetTasksUseCase,
    private val refreshRepositoryUseCase: RefreshRepositoryUseCase
) : ViewModel() {
    val tasks: Flow<List<Task>> = getTasksUseCase.invoke()

    init {
        refreshRepository()
    }

    fun refreshRepository() {
        viewModelScope.launch {
            refreshRepositoryUseCase()
        }
    }
}