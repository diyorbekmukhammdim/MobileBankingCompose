package uz.gita.mobilebanking.ui.screen.signup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.mobilebanking.data.model.common.SignType.SIGN_UP
import uz.gita.mobilebanking.data.model.request.SignUpRequest
import uz.gita.mobilebanking.domain.usecase.SignUpUseCase
import uz.gita.mobilebanking.navigation.AppNavigator
import uz.gita.mobilebanking.ui.screen.signup.SignUpContract.*
import uz.gita.mobilebanking.ui.screen.verify.VerifyScreen
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SignUpScreenModelImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val signUpUseCase: SignUpUseCase
) : ScreenModel, ViewModel {

    override val uiState = MutableStateFlow(UiState())

    override fun onEventDispatcher(intent: Intent) {
        when (intent) {
            is Intent.SignUp -> {
                doSignUp()
            }
            is Intent.Back -> {
                coroutineScope.launch { appNavigator.back() }
            }
            is Intent.PhoneEntered -> {
                reduce { it.copy(phone = intent.phone) }
            }
            is Intent.FirstNameEntered -> {
                reduce { it.copy(firstName = intent.firstName) }
            }
            is Intent.LastNameEntered -> {
                reduce { it.copy(lastName = intent.lastName) }
            }
            is Intent.DateOfBirthEntered -> {
                reduce { it.copy(dateOfBirth = intent.dateOfBirth.toCalFormat()) }
            }
            is Intent.PasswordEntered -> {
                reduce { it.copy(password = intent.password) }
            }
            is Intent.GenderEntered -> {
                reduce { it.copy(gender = intent.gender) }
            }
        }
        check()
    }

    private fun check() {
        val v = uiState.value
        val state =
            v.password.length > 3
                && v.phone.length == 13
                && v.firstName.length > 3
                && v.lastName.length > 3
                && v.dateOfBirth.length == 10
        reduce { it.copy(buttonEnableState = state) }
    }

    private fun reduce(block: (oldState: UiState) -> UiState) {
        val old = uiState.value
        uiState.value = block(old)
    }

    private suspend fun open(screen: Screen) {
        appNavigator.navigationTo(screen)
    }

    private fun doSignUp() = with(uiState.value) {
        reduce { it.copy(progressAlpha = 1f) }
        val data = SignUpRequest(
            phone = this.phone.trim(),
            password = this.password.trim(),
            firstName = this.firstName.trim(),
            lastName = this.lastName.trim(),
            bornDate = this.dateOfBirth.toLongDate(),
            gender = if (this.gender) "0" else "1"
        )
        signUpUseCase.invoke(data)
            .onEach {
                it
                    .onSuccess {
                        reduce { r -> r.copy(progressAlpha = 0f) }
                        open(VerifyScreen(SIGN_UP, uiState.value.phone))
                    }
                    .onFailure { t ->
                        reduce { uiState ->
                            uiState.copy(errorMessage = t.message.toString(), progressAlpha = 0f)
                        }
                    }
            }
            .launchIn(coroutineScope)
    }
}

fun String.toCalFormat(): String {
    if (length == 2 || length == 5) return this.plus("/")
    return this
}

fun String.toLongDate(): String {
    val f = SimpleDateFormat("dd/MM/yyyy")
    try {
        val d: Date = f.parse(this)
        val milliseconds: Long = d.time
        return "$milliseconds"
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}