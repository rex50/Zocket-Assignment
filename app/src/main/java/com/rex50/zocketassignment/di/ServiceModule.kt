package com.rex50.zocketassignment.di

import com.rex50.zocketassignment.data.datasource.remote.services.MetaPageService
import com.rex50.zocketassignment.data.datasource.remote.services.ZocketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesMetaPageService(@Named("meta") retrofit: Retrofit) = retrofit.create(MetaPageService::class.java)

    @Provides
    @Singleton
    fun providesZocketPageService(@Named("zocket") retrofit: Retrofit) = retrofit.create(ZocketService::class.java)

}