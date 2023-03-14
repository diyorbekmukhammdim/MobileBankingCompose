package uz.gita.mobilebanking.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.mobilebanking.data.mapper.toStartScreenEnum
import uz.gita.mobilebanking.data.model.StartScreenEnum
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("MobileData", Context.MODE_PRIVATE)

    var startScreen: StartScreenEnum
        set(value) = pref.edit().putString("START_SCREEN", value.name).apply()
        get() = pref.getString("START_SCREEN", "LOGIN")!!.toStartScreenEnum()

    var accessToken: String
        set(value) = pref.edit().putString("aTOKEN", value).apply()
        get() = pref.getString("aTOKEN", "")!!

    var refreshToken: String
        set(value) = pref.edit().putString("rTOKEN", value).apply()
        get() = pref.getString("rTOKEN", "")!!

    var tempToken: String
        set(value) = pref.edit().putString("tTOKEN", value).apply()
        get() = pref.getString("tTOKEN", "")!!
}