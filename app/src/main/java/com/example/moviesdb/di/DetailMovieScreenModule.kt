package com.example.moviesdb.di

import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.DetailMovieApi
import com.example.moviesdb.data.repository.DetailMovieRepositoryImpl
import com.example.moviesdb.domain.repository.DetailMovieRepository
import com.example.moviesdb.domain.repository.MainMovieRepository
import com.example.moviesdb.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailMovieScreenModule {

    @Provides
    @Singleton
    fun provideCheckMovieInWatchListUseCase(repository: DetailMovieRepository): CheckMovieInWatchList {
        return CheckMovieInWatchList(repository)
    }

    @Provides
    @Singleton
    fun provideInsertMovieToListUseCase(repository: MainMovieRepository): InsertMovieToList {
        return InsertMovieToList(repository)
    }

    @Provides
    @Singleton
    fun provideGetRecommendationUseCase(
        repository: DetailMovieRepository
    ): GetRecommendation {
        return GetRecommendation(repository)
    }

    @Provides
    @Singleton
    fun provideGetImagesByMovieUseCase(
        repository: DetailMovieRepository
    ): GetImagesByMovie {
        return GetImagesByMovie(repository)
    }

    @Provides
    @Singleton
    fun provideGetCastByMovieUseCase(
        repository: DetailMovieRepository
    ): GetCastByMovie {
        return GetCastByMovie(repository)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetails(
        repository: DetailMovieRepository
    ): GetMovieDetails {
        return GetMovieDetails(repository)
    }

    @Provides
    @Singleton
    fun provideDetailMovieRepository(
        api: DetailMovieApi,
        db: MoviesDbDatabase
    ): DetailMovieRepository {
        return DetailMovieRepositoryImpl(api, db)
    }
}