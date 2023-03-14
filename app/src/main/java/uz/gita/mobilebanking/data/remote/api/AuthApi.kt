package uz.gita.mobilebanking.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.mobilebanking.data.model.request.SignInRequest
import uz.gita.mobilebanking.data.model.request.SignUpRequest
import uz.gita.mobilebanking.data.model.request.UpdateToken
import uz.gita.mobilebanking.data.model.request.VerifyRequest
import uz.gita.mobilebanking.data.model.response.AccessTokenData
import uz.gita.mobilebanking.data.model.response.TokenData

interface AuthApi {
    @POST("auth/sign-up")
    suspend fun signUp(@Body data: SignUpRequest): Response<TokenData>

    @POST("auth/sign-up/verify")
    suspend fun signUpVerify(@Body body: VerifyRequest): Response<AccessTokenData>

    @POST("auth/sign-in")
    suspend fun signIn(@Body data: SignInRequest): Response<TokenData>

    @POST("auth/sign-in/verify")
    suspend fun signInVerify(@Body body: VerifyRequest): Response<AccessTokenData>

    @POST("auth/update-token")
    suspend fun updateToken(@Body updateToken: UpdateToken): Response<AccessTokenData>

    @POST("auth/sign-in/resend")
    suspend fun loginResendCode(@Body token: TokenData): Response<TokenData>

    @POST("auth/sign-up/resend")
    suspend fun registerResendCode(@Body token: TokenData): Response<TokenData>
}