package com.rex50.zocketassignment.di

import com.rex50.zocketassignment.data.datasource.local.sharedPrefs.userDetailProvider.UserDetailsProvider
import com.rex50.zocketassignment.data.datasource.local.room.PageLocalDataSourceImpl
import com.rex50.zocketassignment.data.datasource.remote.AuthRemoteDataSourceImpl
import com.rex50.zocketassignment.data.datasource.remote.PageRemoteDataSourceImpl
import com.rex50.zocketassignment.data.repos.meta.auth.AuthRepo
import com.rex50.zocketassignment.data.repos.meta.auth.AuthRepoImpl
import com.rex50.zocketassignment.data.repos.meta.page.PagesRepo
import com.rex50.zocketassignment.data.repos.meta.page.PagesRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun providesPagesRepo(
        localDataSourceImpl: PageLocalDataSourceImpl,
        remoteDataSourceImpl: PageRemoteDataSourceImpl,
        userDetailsProvider: UserDetailsProvider
    ): PagesRepo = PagesRepoImpl(
        remoteDataSourceImpl,
        localDataSourceImpl,
        userDetailsProvider
    )

    @Singleton
    @Provides
    fun providesAuthRepo(
        remoteDataSourceImpl: AuthRemoteDataSourceImpl,
        userDetailsProvider: UserDetailsProvider
    ): AuthRepo = AuthRepoImpl(
        remoteDataSourceImpl,
        userDetailsProvider
    )

}