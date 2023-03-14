package uz.gita.mobilebanking.ui.screen.splash

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.data.model.StartScreenEnum
import uz.gita.mobilebanking.domain.usecase.SignUpUseCase
import uz.gita.mobilebanking.domain.usecase.SplashUseCase
import uz.gita.mobilebanking.navigation.AppNavigator
import uz.gita.mobilebanking.ui.screen.entry.EntryScreen
import uz.gita.mobilebanking.ui.screen.main.MainScreen
import javax.inject.Inject

class SplashScreenModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val splashUseCase: SplashUseCase
) : ScreenModel, SplashContract.ViewModel {

    init { openNextScreen() }

    private fun openNextScreen() {
        splashUseCase.openScreen().onEach {screen->
            when (screen) {
                StartScreenEnum.LOGIN -> appNavigator.replace(EntryScreen())
                StartScreenEnum.HOME -> appNavigator.replace(MainScreen())
            }
        }.launchIn(coroutineScope)
    }
}