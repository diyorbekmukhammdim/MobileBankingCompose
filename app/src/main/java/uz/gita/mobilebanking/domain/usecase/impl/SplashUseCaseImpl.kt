package uz.gita.mobilebanking.domain.usecase.impl

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.mobilebanking.data.model.StartScreenEnum
import uz.gita.mobilebanking.domain.repository.AuthRepository
import uz.gita.mobilebanking.domain.usecase.SplashUseCase
import javax.inject.Inject

class SplashUseCaseImpl @Inject constructor(
    private val repository: AuthRepository
) : SplashUseCase {

    override fun openScreen(): Flow<StartScreenEnum> = flow {
        delay(1200)
        emit(repository.startScreen())
    }
}
