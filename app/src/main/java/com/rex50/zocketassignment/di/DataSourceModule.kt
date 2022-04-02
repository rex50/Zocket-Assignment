package com.rex50.zocketassignment.di

import android.content.Context
import com.rex50.zocketassignment.data.datasource.local.room.AppDatabase
import com.rex50.zocketassignment.data.datasource.local.room.DatabaseBuilder
import com.rex50.zocketassignment.data.datasource.local.room.PageLocalDataSourceImpl
import com.rex50.zocketassignment.data.datasource.local.room.daos.PagesDao
import com.rex50.zocketassignment.data.datasource.remote.AuthRemoteDataSourceImpl
import com.rex50.zocketassignment.data.datasource.remote.PageRemoteDataSourceImpl
import com.rex50.zocketassignment.data.datasource.remote.services.MetaPageService
import com.rex50.zocketassignment.data.datasource.remote.services.ZocketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesPageLocalDataSource(dao: PagesDao): PageLocalDataSourceImpl {
        return PageLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun providesPagesDao(appDatabase: AppDatabase): PagesDao = appDatabase.pagesDao()

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return DatabaseBuilder(context).getInstance()
    }

    @Provides
    @Singleton
    fun providesPageRemoteDataSource(metaService: MetaPageService, zocketService: ZocketService): PageRemoteDataSourceImpl {
        return PageRemoteDataSourceImpl(metaService, zocketService)
    }

    @Provides
    @Singleton
    fun providesAuthRemoteDataSource(metaService: MetaPageService): AuthRemoteDataSourceImpl {
        return AuthRemoteDataSourceImpl(metaService)
    }

}