package uz.gita.mobilebanking.data.model.request

import com.google.gson.annotations.SerializedName

data class UpdateToken(
    @SerializedName("refresh-token")
    val refreshToken: String
)