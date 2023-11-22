package me.reflect.todo.domain.core.usecases

import me.reflect.todo.data.core.model.Task
import me.reflect.todo.data.core.repository.CoreRepository
import org.koin.core.annotation.Single

@Single
class AddTaskUseCase(
    private val coreRepository: CoreRepository
) {
    suspend operator fun invoke(task: Task): Boolean {
        return coreRepository.addTask(task)
    }
}