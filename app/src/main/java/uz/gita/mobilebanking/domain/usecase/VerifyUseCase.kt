package uz.gita.mobilebanking.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.common.SignType

interface VerifyUseCase {
    fun invoke(type: SignType, code: String): Flow<Result<Unit>>
}
