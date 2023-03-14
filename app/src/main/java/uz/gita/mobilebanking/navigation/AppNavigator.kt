package uz.gita.mobilebanking.navigation

import cafe.adriel.voyager.core.screen.Screen


interface AppNavigator {
    suspend fun back()
    suspend fun navigationTo(screen: Screen)
    suspend fun replace(screen: Screen)
    suspend fun replaceAll(screen: Screen)
}

