package uz.gita.mobilebanking.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.request.SignInRequest

interface SignInUseCase {
    fun invoke(signIn: SignInRequest): Flow<Result<Unit>>
}
