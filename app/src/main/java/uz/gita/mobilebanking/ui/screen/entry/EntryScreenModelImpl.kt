package uz.gita.mobilebanking.ui.screen.entry

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.navigation.AppNavigator
import uz.gita.mobilebanking.ui.screen.entry.EntryContract.*
import uz.gita.mobilebanking.ui.screen.signin.SignInScreen
import javax.inject.Inject

class EntryScreenModelImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : ScreenModel, ViewModel {
    override val uiState = MutableStateFlow(UiState(false))

    override fun onEventDispatcher(intent: Intent) {
        when (intent) {
            is Intent.ChangeAgree -> { reduce { it.copy(agreed = !it.agreed) } }
            is Intent.Login -> { coroutineScope.launch { toLoginScreen() } }
        }
    }

    private fun reduce(block: (oldState: UiState) -> UiState) {
        val old = uiState.value
        uiState.value = block(old)
    }

    private suspend fun toLoginScreen() { appNavigator.navigationTo(SignInScreen()) }
}