package uz.gita.mobilebanking.ui.screen.main

import kotlinx.coroutines.flow.StateFlow

interface MainContract {

    interface ViewModel {
        val uiState: StateFlow<UiState>

        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val phone: String = "+998",
        val firstName: String = "",
        val lastName: String = "",
        val dateOfBirth: String = "",
        val password: String = "",
        val errorMessage: String = "",
        val gender: Boolean = true,
        val buttonEnableState: Boolean = false,
    )

    sealed interface Intent {
        class PhoneEntered(val phone: String) : Intent
        class FirstNameEntered(val firstName: String) : Intent
        class LastNameEntered(val lastName: String) : Intent
        class DateOfBirthEntered(val dateOfBirth: String) : Intent
        class PasswordEntered(val password: String) : Intent
        class GenderEntered(val gender: Boolean) : Intent

        object SignUp : Intent
        object Back : Intent
    }
}