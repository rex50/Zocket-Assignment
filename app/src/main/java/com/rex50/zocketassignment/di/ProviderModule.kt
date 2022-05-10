package com.rex50.zocketassignment.di

import com.rex50.zocketassignment.data.datasource.local.sharedPrefs.userDetailProvider.UserDetailsProvider
import com.rex50.zocketassignment.data.datasource.local.sharedPrefs.userDetailProvider.UserDetailsProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    fun providesUserDetailsProvider(): UserDetailsProvider = UserDetailsProviderImpl()

}