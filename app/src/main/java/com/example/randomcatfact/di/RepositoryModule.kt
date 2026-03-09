package com.example.randomcatfact.di

import com.example.randomcatfact.data.repository.CatRepository
import com.example.randomcatfact.data.repository.CatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCatRepository(
        impl: CatRepositoryImpl
    ): CatRepository
}