package com.boukhari.orangebank.di

import com.boukhari.orangebank.domain.GetRepoUseCase
import com.boukhari.orangebank.domain.GetRepoUseCaseImpl
import com.boukhari.orangebank.domain.RepoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesGetRepoUseCase(repository: RepoRepository): GetRepoUseCase {
        return GetRepoUseCaseImpl(repository)
    }
}

