package com.example.core.di

import com.example.core.SomeInterface
import com.example.core.SomeInterfaceImpl
import com.example.core.SomeInterfaceImpl2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class)
object SomeInterfaceModule {

    @ActivityScoped
    @Provides
    @Impl1
    fun providesSomeInterface(): SomeInterface {
        return SomeInterfaceImpl()
    }

    @ActivityScoped
    @Provides
    @Impl2
    fun providesSomeInterface2(): SomeInterface {
        return SomeInterfaceImpl2()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2

