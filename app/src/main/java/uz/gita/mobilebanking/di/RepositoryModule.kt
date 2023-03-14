package uz.gita.mobilebanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import uz.gita.mobilebanking.domain.repository.AuthRepository
import uz.gita.mobilebanking.domain.repository.AuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun getAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}