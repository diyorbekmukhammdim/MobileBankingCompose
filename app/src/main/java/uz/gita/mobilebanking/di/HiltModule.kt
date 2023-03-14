package uz.gita.mobilebanking.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import uz.gita.mobilebanking.ui.screen.entry.EntryScreenModelImpl
import uz.gita.mobilebanking.ui.screen.main.MainScreen
import uz.gita.mobilebanking.ui.screen.main.MainScreenModelImpl
import uz.gita.mobilebanking.ui.screen.signin.SignInScreenModelImpl
import uz.gita.mobilebanking.ui.screen.signup.SignUpScreenModelImpl
import uz.gita.mobilebanking.ui.screen.splash.SplashScreenModelImpl
import uz.gita.mobilebanking.ui.screen.verify.VerifyScreenModelImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class HiltModule {
    @[Binds IntoMap ScreenModelKey(SplashScreenModelImpl::class)]
    abstract fun bindSplashVM(impl: SplashScreenModelImpl): ScreenModel

    @[Binds IntoMap ScreenModelKey(EntryScreenModelImpl::class)]
    abstract fun bindEntryVM(impl: EntryScreenModelImpl): ScreenModel

    @[Binds IntoMap ScreenModelKey(SignInScreenModelImpl::class)]
    abstract fun bindSignInVM(impl: SignInScreenModelImpl): ScreenModel

    @[Binds IntoMap ScreenModelKey(SignUpScreenModelImpl::class)]
    abstract fun bindSignUpVM(impl: SignUpScreenModelImpl): ScreenModel

    @[Binds IntoMap ScreenModelKey(VerifyScreenModelImpl::class)]
    abstract fun bindVerifyVM(impl: VerifyScreenModelImpl): ScreenModel

    @[Binds IntoMap ScreenModelKey(MainScreenModelImpl::class)]
    abstract fun bindMainVM(impl: MainScreenModelImpl): ScreenModel
}