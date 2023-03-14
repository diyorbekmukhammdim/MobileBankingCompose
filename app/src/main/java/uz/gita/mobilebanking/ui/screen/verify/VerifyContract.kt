package uz.gita.mobilebanking.ui.screen.verify

import kotlinx.coroutines.flow.StateFlow
import uz.gita.mobilebanking.R
import uz.gita.mobilebanking.data.model.common.SignType

class VerifyContract {

    interface ViewModel {
        val uiState: StateFlow<UiState>

        fun onEventDispatcher(intent: Intent)
    }

    data class UiState(
        val phone: String = "+998*********",
        val type: SignType = SignType.SIGN_IN,
        val code: String = "",
        val errorMessage: String = "",
        val progress: Boolean = false,
        val resend: Boolean = false,
        val buttonEnableState: Boolean = false,
        var timer: Int = 59,
        val isError: Boolean = false,
        val errorText: Int = R.string.invalid_sms_code,
    )

    sealed interface Intent {
        class CodeEntered(val code: String) : Intent
        class SetPhone(val phone: String) : Intent
        class SetVerifyType(val type: SignType) : Intent
        class ButtonState(val state: Boolean) : Intent
        object EditStart : Intent
        object Back : Intent
        object Verify : Intent
        object ResendCode : Intent
    }
}

