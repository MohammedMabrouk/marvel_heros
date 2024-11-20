package com.mabrouk.mohamed.marvelheros.di

import com.mabrouk.mohamed.marvelheros.data.remote.ApiService
import com.mabrouk.mohamed.marvelheros.data.repository.MarvelRepositoryImpl
import com.mabrouk.mohamed.marvelheros.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMarvelRepository(apiService: ApiService): MarvelRepository =
        MarvelRepositoryImpl(apiService)
}