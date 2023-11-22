package me.reflect.todo.domain.core.usecases

import me.reflect.todo.domain.repository.CoreRepository
import org.koin.core.annotation.Single

@Single
class GetTaskByIdUseCase(private val coreRepository: CoreRepository) {
    suspend operator fun invoke(id: String) = coreRepository.getTaskById(id)
}