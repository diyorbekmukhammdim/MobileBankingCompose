package uz.gita.mobilebanking.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.mobilebanking.data.model.StartScreenEnum
import uz.gita.mobilebanking.data.model.request.SignInRequest
import uz.gita.mobilebanking.data.model.request.SignUpRequest

interface AuthRepository {
    fun signIn(data: SignInRequest): Flow<Result<Unit>>
    fun signUp(data: SignUpRequest): Flow<Result<Unit>>

    fun signInVerify(code: String): Flow<Result<Unit>>
    fun signUpVerify(code: String): Flow<Result<Unit>>

    suspend fun startScreen(): StartScreenEnum

    fun loginResend(): Flow<Result<Unit>>
    fun registerResend(): Flow<Result<Unit>>
}