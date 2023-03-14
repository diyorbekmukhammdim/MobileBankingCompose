package uz.gita.mobilebanking.ui.screen.splash

interface SplashContract {

    interface ViewModel {
//        val uiState: StateFlow<UiState>

//        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val count: Int
    )

    sealed interface Intent {
        object OpenNext : Intent
    }
}