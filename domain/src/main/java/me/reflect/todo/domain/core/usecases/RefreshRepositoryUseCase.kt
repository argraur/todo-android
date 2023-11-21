package me.reflect.todo.domain.core.usecases

import me.reflect.todo.data.core.repository.CoreRepository
import org.koin.core.annotation.Single

@Single
class RefreshRepositoryUseCase(
    private val coreRepository: CoreRepository
) {
    suspend operator fun invoke() {
        coreRepository.syncRepository()
    }
}