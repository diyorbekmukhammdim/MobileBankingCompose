package uz.gita.mobilebanking.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.request.SignUpRequest

interface SignUpUseCase {
    fun invoke(signUp: SignUpRequest): Flow<Result<Unit>>
}
