package uz.gita.mobilebanking.data.model.response

data class CardResponse(
	val owner: String,
	val amount: Int,
	val themeType: Int,
	val name: String,
	val expiredYear: Int,
	val expiredMonth: Int,
	val id: Int,
	val isVisible: Boolean,
	val pan: String
)
