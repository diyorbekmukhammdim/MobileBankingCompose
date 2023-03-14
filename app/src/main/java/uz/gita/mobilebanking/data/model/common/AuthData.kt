package uz.gita.mobilebanking.data.model.common

sealed class AuthData {

    data class VerifyData(val code: String)
}
