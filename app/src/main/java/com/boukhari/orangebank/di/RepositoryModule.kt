package com.boukhari.orangebank.di


import com.boukhari.orangebank.data.local.RepoDao
import com.boukhari.orangebank.data.remote.ApiRepositoryImpl
import com.boukhari.orangebank.data.remote.RetrofitService
import com.boukhari.orangebank.domain.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideArticlesRepository(
        remoteService: RetrofitService,
        localService: RepoDao,
    ): RepoRepository {
        return ApiRepositoryImpl(remoteService, localService)
    }
}

