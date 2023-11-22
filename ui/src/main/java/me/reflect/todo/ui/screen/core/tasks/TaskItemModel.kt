package me.reflect.todo.ui.screen.core.tasks

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.reflect.todo.domain.core.model.Task

class TaskItemModel(task: Task) : ViewModel() {
    private val _state = MutableStateFlow(task)
    val state = _state.asStateFlow()
}