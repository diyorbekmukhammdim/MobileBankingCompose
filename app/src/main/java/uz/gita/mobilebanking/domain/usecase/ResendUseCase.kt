package uz.gita.mobilebanking.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.common.SignType

interface ResendUseCase {
    fun invoke(type: SignType): Flow<Result<Unit>>
}
