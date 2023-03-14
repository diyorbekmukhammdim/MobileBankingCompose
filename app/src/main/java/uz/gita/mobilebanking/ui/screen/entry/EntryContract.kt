package uz.gita.mobilebanking.ui.screen.entry

import kotlinx.coroutines.flow.StateFlow

interface EntryContract {

    interface ViewModel {
        val uiState: StateFlow<UiState>

        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val agreed: Boolean
    )

    sealed interface Intent {
        object ChangeAgree : Intent
        object Login : Intent
    }
}