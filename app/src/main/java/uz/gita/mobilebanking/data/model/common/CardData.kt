package uz.gita.mobilebanking.data.model.common

data class CardData(
	val pan: String,
	val expiredYear: String,
	val expiredMonth: String,
	val name: String,
)
