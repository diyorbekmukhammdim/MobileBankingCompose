package uz.gita.mobilebanking.data.model.request

data class VerifyRequest(
    val token: String,
    val code: String,
)