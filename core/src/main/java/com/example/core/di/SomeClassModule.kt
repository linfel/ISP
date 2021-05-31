package com.example.core.di

import com.example.core.SomeClass
import com.example.core.SomeOtherClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object SomeClassModule {

    @Provides
    @ViewModelScoped
    fun providesSomeClass(someOtherClass: SomeOtherClass): SomeClass {
        return SomeClass(someOtherClass)
    }

}