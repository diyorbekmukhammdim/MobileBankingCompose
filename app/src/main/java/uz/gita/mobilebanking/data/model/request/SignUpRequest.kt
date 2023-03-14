package uz.gita.mobilebanking.data.model.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("first-name")
    val firstName: String,
    @SerializedName("last-name")
    val lastName: String,
    @SerializedName("born-date")
    val bornDate: String,
    @SerializedName("gender")
    val gender: String,
)