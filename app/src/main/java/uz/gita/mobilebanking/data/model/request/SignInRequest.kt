package uz.gita.mobilebanking.data.model.request

data class SignInRequest(
    val phone: String,
    val password: String,
)