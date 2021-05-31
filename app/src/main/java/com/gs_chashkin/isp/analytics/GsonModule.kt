package com.gs_chashkin.isp.analytics

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class GsonModule {

    @ActivityScoped
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}