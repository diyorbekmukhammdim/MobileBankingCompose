package uz.gita.mobilebanking.ui.screen.signin

import kotlinx.coroutines.flow.StateFlow

interface SignInContract {

    interface ViewModel {
        val uiState: StateFlow<UiState>

        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val phone: String = "+998",
        val password: String = "",
        val progressAlpha: Float = 0f,
        var errorMessage: String = "",
        val passwordVisible: Boolean = false,
        val buttonEnableState: Boolean = false,
    )

    sealed interface Intent {
        class PhoneEntered(val phone: String) : Intent
        class PasswordEntered(val password: String) : Intent

        object ShowPassword : Intent
        object Back : Intent
        object Login : Intent
        object CreateAccount : Intent
    }
}