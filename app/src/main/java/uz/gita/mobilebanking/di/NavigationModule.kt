package uz.gita.mobilebanking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.mobilebanking.navigation.AppNavigator
import uz.gita.mobilebanking.navigation.MyNavigationManager
import uz.gita.mobilebanking.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindNavigationHandler(impl: MyNavigationManager): NavigationHandler

    @Binds
    fun bindAppNavigator(impl: MyNavigationManager): AppNavigator
}