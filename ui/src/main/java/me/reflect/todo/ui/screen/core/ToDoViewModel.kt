package me.reflect.todo.ui.screen.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.reflect.todo.domain.core.usecases.AddTaskUseCase
import me.reflect.todo.domain.auth.usecases.FetchUserUseCase
import me.reflect.todo.domain.core.usecases.GetTasksUseCase
import me.reflect.todo.domain.auth.usecases.GetUserUseCase
import me.reflect.todo.domain.core.model.Task
import me.reflect.todo.domain.core.usecases.RefreshRepositoryUseCase
import me.reflect.todo.domain.core.usecases.RemoveTaskUseCase
import org.koin.android.annotation.KoinViewModel

data class ToDoState(
    val isLoading: Boolean = false,
    val isDialogOpen: Boolean = false,
    val isError: Boolean = false,
    val searchVisible: Boolean = false,
    val errorMsg: String = "",
    val filterMode: FilterMode = FilterMode.NONE
)

@KoinViewModel
class ToDoViewModel(
    getTasksUseCase: GetTasksUseCase,
    private val refreshRepositoryUseCase: RefreshRepositoryUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val removeTaskUseCase: RemoveTaskUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val fetchUserUseCase: FetchUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ToDoState())
    val uiState: StateFlow<ToDoState> = _uiState.asStateFlow()

    val tasks: Flow<List<Task>> = getTasksUseCase.invoke()

    private val user = getUserUseCase()

    init {
        refreshUser()
        refreshRepository()
    }

    fun refreshRepository() {
        viewModelScope.launch {
            runWithVisibleProgress {
                delay(2000)
                refreshRepositoryUseCase()
            }
        }
    }

    private fun refreshUser() {
        viewModelScope.launch {
            fetchUserUseCase()
        }
    }

    private suspend fun runWithVisibleProgress(runnable: suspend () -> Unit) {
        _uiState.update { it.copy(isLoading = true) }
        runnable()
        _uiState.update { it.copy(isLoading = false) }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            runWithVisibleProgress {
                // set user id
                val userId = user.first().userId
                task.creatorId = userId
                task.assigneeId = listOf(userId)

                val result = addTaskUseCase(task)
                if (!result) {
                    _uiState.update {
                        it.copy(isError = true, errorMsg = "Не удалось сохранить задачу")
                    }
                }
            }

        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            delay(300)
            val result = removeTaskUseCase.invoke(task)
            if (!result) {
                _uiState.update {
                    it.copy(isError = true, errorMsg = "Не удалось удалить задачу")
                }
                refreshRepository()
            }
        }
    }

    fun toggleTaskEditDialog() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isDialogOpen = !it.isDialogOpen)
            }
        }
    }

    fun endError() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isError = false, errorMsg = "")
            }
        }
    }

    fun toggleSearchBar() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(searchVisible = !it.searchVisible)
            }
        }
    }

    fun toggleFilterMode(filterMode: FilterMode) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(filterMode =
                    if (it.filterMode == filterMode)
                        FilterMode.NONE
                    else
                        filterMode
                )
            }
        }
    }

    fun closeTask(taskId: String) {

    }
}