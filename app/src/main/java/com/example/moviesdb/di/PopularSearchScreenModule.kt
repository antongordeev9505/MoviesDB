package com.example.moviesdb.di

import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.data.repository.PopularSearchMoviesRepositoryImpl
import com.example.moviesdb.domain.repository.PopularSearchMoviesRepository
import com.example.moviesdb.domain.use_case.GetPopularMovies
import com.example.moviesdb.domain.use_case.SearchMoviesByQuery
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PopularSearchScreenModule {

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(repository: PopularSearchMoviesRepository): GetPopularMovies {
        return GetPopularMovies(repository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesByQueryUseCase(repository: PopularSearchMoviesRepository): SearchMoviesByQuery {
        return SearchMoviesByQuery(repository)
    }

    @Provides
    @Singleton
    fun providePopularSearchMoviesRepository(
        api: MoviesDbApi
    ): PopularSearchMoviesRepository {
        return PopularSearchMoviesRepositoryImpl(api)
    }
}
