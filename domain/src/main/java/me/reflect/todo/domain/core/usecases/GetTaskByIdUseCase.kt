package me.reflect.todo.domain.core.usecases

import me.reflect.todo.data.core.model.Task
import me.reflect.todo.data.core.repository.CoreRepository
import org.koin.core.annotation.Single

@Single
class GetTaskByIdUseCase(private val coreRepository: CoreRepository) {
    suspend operator fun invoke(id: String): Task {
        return coreRepository.getTaskById(id)
    }
}