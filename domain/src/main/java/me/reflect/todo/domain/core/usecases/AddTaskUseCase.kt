package me.reflect.todo.domain.core.usecases

import me.reflect.todo.domain.core.model.Task
import me.reflect.todo.domain.repository.CoreRepository
import org.koin.core.annotation.Single

@Single
class AddTaskUseCase(
    private val coreRepository: CoreRepository
) {
    suspend operator fun invoke(task: Task): Boolean {
        return coreRepository.addTask(task)
    }
}