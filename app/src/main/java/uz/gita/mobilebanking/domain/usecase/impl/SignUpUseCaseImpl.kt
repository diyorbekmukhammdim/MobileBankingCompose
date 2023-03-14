package uz.gita.mobilebanking.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.request.SignUpRequest
import uz.gita.mobilebanking.domain.repository.AuthRepository
import uz.gita.mobilebanking.domain.usecase.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(private val repository: AuthRepository) : SignUpUseCase {
    override fun invoke(signUp: SignUpRequest): Flow<Result<Unit>> = repository.signUp(signUp)
}
