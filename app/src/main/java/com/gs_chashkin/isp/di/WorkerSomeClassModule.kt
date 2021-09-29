package com.gs_chashkin.isp.di

import com.example.core.WorkerSomeClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerSomeClassModule {

    @Singleton
    @Provides
    fun provideWorkerSomeClass(): WorkerSomeClass {
        return WorkerSomeClass()
    }

}