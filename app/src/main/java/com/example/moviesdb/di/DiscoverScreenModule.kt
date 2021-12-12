package com.example.moviesdb.di

import com.example.moviesdb.data.remote.DiscoverMoviesApi
import com.example.moviesdb.data.repository.DiscoverMoviesRepositoryImpl
import com.example.moviesdb.domain.repository.DiscoverMoviesRepository
import com.example.moviesdb.domain.use_case.DiscoverMovies
import com.example.moviesdb.domain.use_case.GetGenres
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiscoverScreenModule {

    @Provides
    @Singleton
    fun provideGetGenresUseCase(repository: DiscoverMoviesRepository): GetGenres {
        return GetGenres(repository)
    }

    @Provides
    @Singleton
    fun provideDiscoverMoviesUseCase(repository: DiscoverMoviesRepository): DiscoverMovies {
        return DiscoverMovies(repository)
    }

    @Provides
    @Singleton
    fun provideDiscoverMoviesRepository(
        api: DiscoverMoviesApi
    ): DiscoverMoviesRepository {
        return DiscoverMoviesRepositoryImpl(api)
    }
}