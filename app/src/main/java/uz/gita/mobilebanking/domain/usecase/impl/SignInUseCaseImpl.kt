package uz.gita.mobilebanking.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.mobilebanking.data.model.request.SignInRequest
import uz.gita.mobilebanking.domain.repository.AuthRepository
import uz.gita.mobilebanking.domain.usecase.SignInUseCase
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(private val repository: AuthRepository) : SignInUseCase {
    override fun invoke(signIn: SignInRequest): Flow<Result<Unit>> = repository.signIn(signIn)
}
