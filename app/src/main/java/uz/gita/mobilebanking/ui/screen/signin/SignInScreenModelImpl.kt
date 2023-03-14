package uz.gita.mobilebanking.ui.screen.signin

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.data.model.common.SignType.SIGN_IN
import uz.gita.mobilebanking.data.model.request.SignInRequest
import uz.gita.mobilebanking.domain.usecase.SignInUseCase
import uz.gita.mobilebanking.navigation.AppNavigator
import uz.gita.mobilebanking.ui.screen.signin.SignInContract.*
import uz.gita.mobilebanking.ui.screen.signup.SignUpScreen
import uz.gita.mobilebanking.ui.screen.verify.VerifyScreen
import uz.gita.mobilebanking.utils.ConnectionUtil
import javax.inject.Inject

class SignInScreenModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val connectionUtil: ConnectionUtil,
    private val signInUseCase: SignInUseCase
) : ScreenModel, ViewModel {

    override val uiState = MutableStateFlow(UiState())

    override fun onEventDispatcher(intent: Intent) {
        when (intent) {
            is Intent.Login -> {
                doSignIn()
            }
            is Intent.CreateAccount -> {
                coroutineScope.launch { open(SignUpScreen()) }
            }
            is Intent.ShowPassword -> {
                reduce { it.copy(passwordVisible = !it.passwordVisible) }
            }
            is Intent.PasswordEntered -> {
                reduce { it.copy(password = intent.password) }
            }
            is Intent.PhoneEntered -> {
                reduce { it.copy(phone = intent.phone) }
            }
            is Intent.Back -> {
                coroutineScope.launch { appNavigator.back() }
            }
        }
        check()
    }

    private fun check(){
        val state = uiState.value.password.length >3 && uiState.value.phone.length == 13
        reduce { it.copy( buttonEnableState = state) }
    }

    private fun reduce(block: (oldState: UiState) -> UiState) {
        val old = uiState.value
        uiState.value = block(old)
    }

    private suspend fun open(screen: Screen) {
        appNavigator.navigationTo(screen)
    }

    private fun doSignIn() {
        reduce { it.copy(progressAlpha = 1f) }
        if (!connectionUtil.isConnected()) {
            uiState.value.errorMessage = "No internet connection"
            reduce { it.copy(progressAlpha = 0f) }
            return
        }
        signInUseCase.invoke(
            SignInRequest(
                phone = uiState.value.phone,
                password = uiState.value.password.trim()
            )
        )
            .onEach {
                it
                    .onSuccess {
                        reduce { r -> r.copy(progressAlpha = 0f) }
                        open(VerifyScreen(SIGN_IN, uiState.value.phone))
                    }
                    .onFailure { t ->
                        reduce { uiState -> uiState.copy(errorMessage = t.message.toString(), progressAlpha = 0f) }
                    }
            }
            .launchIn(coroutineScope)
    }
}