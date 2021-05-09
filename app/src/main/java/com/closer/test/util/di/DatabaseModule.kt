package com.closer.test.util.di

import android.content.Context
import com.closer.test.util.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext app: Context): AppDatabase = AppDatabase.invoke(app)
}