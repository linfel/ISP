package com.gs_chashkin.isp.di

import android.content.Context
import com.gs_chashkin.isp.main.IspApplication
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
    fun provideApplication(@ApplicationContext  app: Context): IspApplication {
        return app as IspApplication
    }

    @Singleton
    @Provides
    fun provideRandomString(): String {
        return "Hey look random string"
    }
}