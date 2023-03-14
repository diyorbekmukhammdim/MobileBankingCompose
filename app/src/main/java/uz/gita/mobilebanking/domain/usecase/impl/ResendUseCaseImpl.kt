package uz.gita.mobilebanking.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.common.SignType
import uz.gita.mobilebanking.domain.repository.AuthRepository
import uz.gita.mobilebanking.domain.usecase.ResendUseCase
import uz.gita.mobilebanking.domain.usecase.VerifyUseCase
import javax.inject.Inject

class ResendUseCaseImpl @Inject constructor(private val repository: AuthRepository) : ResendUseCase {
    override fun invoke(type: SignType): Flow<Result<Unit>> =
        when (type) {
            SignType.SIGN_IN -> repository.loginResend()
            SignType.SIGN_UP -> repository.registerResend()
        }
}
