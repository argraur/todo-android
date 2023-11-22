package me.reflect.todo.domain.core.usecases

import me.reflect.todo.domain.repository.CoreRepository
import org.koin.core.annotation.Single

@Single
class GetTasksUseCase(
    private val coreRepository: CoreRepository
) {
    operator fun invoke() = coreRepository.getMyTasksFlow()
}