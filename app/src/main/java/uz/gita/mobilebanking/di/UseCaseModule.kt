package uz.gita.mobilebanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mobilebanking.domain.usecase.*
import uz.gita.mobilebanking.domain.usecase.impl.*

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Binds
    fun getLoginUseCase(impl: SignInUseCaseImpl): SignInUseCase

    @Binds
    fun getSplashUseCase(impl: SplashUseCaseImpl): SplashUseCase

    @Binds
    fun getDashboardUseCase(impl: DashboardUseCaseImpl): DashboardUseCase

    @Binds
    fun getRegisterUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    fun getVerifyUseCase(impl: VerifyUseCaseImpl): VerifyUseCase

    @Binds
    fun getResendUseCase(impl: ResendUseCaseImpl): ResendUseCase
}