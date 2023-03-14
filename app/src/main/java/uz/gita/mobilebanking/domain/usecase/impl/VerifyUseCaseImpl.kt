package uz.gita.mobilebanking.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.common.SignType
import uz.gita.mobilebanking.domain.repository.AuthRepository
import uz.gita.mobilebanking.domain.usecase.VerifyUseCase
import javax.inject.Inject

class VerifyUseCaseImpl @Inject constructor(private val repository: AuthRepository) : VerifyUseCase {
    override fun invoke(type: SignType, code: String): Flow<Result<Unit>> =
        when (type) {
            SignType.SIGN_IN -> repository.signInVerify(code)
            SignType.SIGN_UP -> repository.signUpVerify(code)
        }
}
