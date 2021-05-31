package com.gs_chashkin.isp

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import retrofit2.Retrofit
import javax.inject.Inject

interface AnalyticsService {
    fun analyticsMethods()
}

// Constructor-injected, because Hilt needs to know how to
// provide instances of AnalyticsServiceImpl, too.
class AnalyticsServiceImpl @Inject constructor(

) : AnalyticsService {
    override fun analyticsMethods() {
        TODO("Not yet implemented")
    }
}

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


