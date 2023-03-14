package uz.gita.mobilebanking.ui.screen.verify

import android.util.Log
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.domain.usecase.ResendUseCase
import uz.gita.mobilebanking.domain.usecase.VerifyUseCase
import uz.gita.mobilebanking.navigation.AppNavigator
import uz.gita.mobilebanking.ui.screen.main.MainScreen
import uz.gita.mobilebanking.ui.screen.verify.VerifyContract.*
import javax.inject.Inject

class VerifyScreenModelImpl @Inject constructor(
    private val verifyUseCase: VerifyUseCase,
    private val resendUseCase: ResendUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel, ScreenModel {

    override val uiState = MutableStateFlow(UiState())

    init { startTimer() }

    private fun startTimer() {
        coroutineScope.launch {
            repeat(59) {
                delay(1000)
                reduce { it.copy(timer = it.timer - 1) }
            }
            delay(500)
            reduce { it.copy(resend = true) }
        }
    }

    override fun onEventDispatcher(intent: Intent) {
        when (intent) {
            Intent.Verify -> {
                verify()
            }
            is Intent.CodeEntered -> {
                reduce { it.copy(code = intent.code, isError = false, errorMessage = "") }
            }
            Intent.Back -> {
                back()
            }
            is Intent.ButtonState -> {
                reduce { it.copy(buttonEnableState = intent.state) }
            }
            is Intent.SetPhone -> {
                reduce { it.copy(phone = intent.phone) }
            }
            is Intent.SetVerifyType -> {
                reduce { it.copy(type = intent.type) }
            }
            is Intent.ResendCode -> {
                resendCode()
                reduce { it.copy(isError = false, errorMessage = "") }
            }
            is Intent.EditStart -> {
                reduce { it.copy(isError = false) }
            }
        }
    }

    private fun resendCode() {
        if (!uiState.value.resend) return
        reduce { it.copy(resend = false, timer = 59) }
        startTimer()
        resendUseCase
            .invoke(uiState.value.type)
            .onEach {
                it
                    .onSuccess {  }
                    .onFailure {  }
            }.launchIn(coroutineScope)
    }

    private fun verify() {
        verifyUseCase
            .invoke(uiState.value.type, uiState.value.code)
            .onEach { result ->
                reduce {
                    it.copy(progress = true)
                }
                result.onSuccess {
                    open(MainScreen())
                }
                result.onFailure { t ->
                    reduce {
                        it.copy(progress = false, errorMessage = t.message.toString(), isError = true)
                    }
                }
            }.launchIn(coroutineScope)
    }

    private fun back() {
        coroutineScope.launch { appNavigator.back() }
    }

    private fun open(screen: Screen) {
        coroutineScope.launch { appNavigator.replaceAll(screen) }
    }

    private fun reduce(block: (oldState: UiState) -> UiState) {
        val old = uiState.value
        uiState.value = block(old)
    }
}