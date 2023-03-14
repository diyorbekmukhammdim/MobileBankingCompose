package uz.gita.mobilebanking.domain.repository

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import uz.gita.mobilebanking.data.local.MyPreferences
import uz.gita.mobilebanking.data.mapper.toStartScreenEnum
import uz.gita.mobilebanking.data.model.StartScreenEnum
import uz.gita.mobilebanking.data.model.request.SignInRequest
import uz.gita.mobilebanking.data.model.request.VerifyRequest
import uz.gita.mobilebanking.data.model.request.SignUpRequest
import uz.gita.mobilebanking.data.model.response.MessageResponse
import uz.gita.mobilebanking.data.model.response.TokenData
import uz.gita.mobilebanking.data.remote.api.AuthApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val localStorage: MyPreferences,
    private val gson: Gson
) : AuthRepository {

    override fun signUp(data: SignUpRequest) = flow<Result<Unit>> {
        val response = authApi.signUp(data)
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.tempToken = it.token
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun signUpVerify(code: String) = flow<Result<Unit>> {
        val response = authApi.signUpVerify(VerifyRequest(localStorage.tempToken, code))
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.accessToken = it.accessToken
                localStorage.refreshToken = it.refreshToken
                localStorage.startScreen = "HOME".toStartScreenEnum()
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun signIn(data: SignInRequest) = flow<Result<Unit>> {
        val response = authApi.signIn(data)
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.tempToken = it.token
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun signInVerify(code: String) = flow<Result<Unit>> {
        val response = authApi.signInVerify(VerifyRequest(localStorage.tempToken, code))
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.accessToken = it.accessToken
                localStorage.refreshToken = it.refreshToken
                localStorage.startScreen = "HOME".toStartScreenEnum()
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun loginResend() = flow<Result<Unit>> {
        val response = authApi.loginResendCode(TokenData(localStorage.tempToken))
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.tempToken = it.token
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    override fun registerResend() = flow<Result<Unit>> {
        val response = authApi.registerResendCode(TokenData(localStorage.tempToken))
        if (response.isSuccessful) {
            response.body()?.let {
                localStorage.tempToken = it.token
                emit(Result.success(Unit))
            }
        } else emit(Result.failure(Exception(gsonConverter(response))))
    }.catch { emit(Result.failure(Exception(it.message))) }
        .flowOn(Dispatchers.IO)

    private fun gsonConverter(response: Response<*>): String {
        if (response.errorBody() == null) return ""
        val messageResponse = gson.fromJson(response.errorBody()?.string(), MessageResponse::class.java)
        return messageResponse.message ?: ""
    }

    override suspend fun startScreen(): StartScreenEnum = localStorage.startScreen
}