package com.gs_chashkin.isp.di

import com.gs_chashkin.isp.analytics.AnalyticsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit

//@Module
//@InstallIn(ActivityComponent::class)
//abstract class AnalyticsModule {
//
//    @Binds
//    abstract fun bindAnalyticsService(
//        analyticsServiceImpl: AnalyticsServiceImpl
//    ): AnalyticsService
//
//}

@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModuleTwo {

    @Provides
    fun provideAnalyticsService(
        // Potential dependencies of this type
    ): AnalyticsService {
        return Retrofit.Builder()
            .baseUrl("https://google.com")
            .build()
            .create(AnalyticsService::class.java)
    }
}