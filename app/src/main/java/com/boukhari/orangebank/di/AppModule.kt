package com.boukhari.orangebank.di

import android.content.Context
import com.boukhari.orangebank.OrangeBankApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): OrangeBankApplication {
        return app as OrangeBankApplication
    }
}