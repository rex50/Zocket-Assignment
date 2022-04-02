package com.rex50.zocketassignment.di

import com.rex50.zocketassignment.data.datasource.interfaces.UserDetailsProvider
import com.rex50.zocketassignment.data.datasource.local.sharedPrefs.UserDetailsProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    fun providesUserDetailsProvider(): UserDetailsProvider = UserDetailsProviderImpl()

}