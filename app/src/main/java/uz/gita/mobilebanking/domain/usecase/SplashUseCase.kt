package uz.gita.mobilebanking.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.StartScreenEnum

interface SplashUseCase {
    fun openScreen(): Flow<StartScreenEnum>
}
