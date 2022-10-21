package com.boukhari.orangebank.di

import android.content.Context
import com.boukhari.orangebank.data.local.AppDatabase
import com.boukhari.orangebank.data.local.RepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideRepoDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.repoDao()
    }
}