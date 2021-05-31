package com.example.core.di

import com.example.core.SomeInterface
import com.example.core.SomeInterfaceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class SomeInterfaceModule {

    @ActivityScoped
    @Provides
    fun providesSomeInterface(): SomeInterface {
        return SomeInterfaceImpl()
    }
}