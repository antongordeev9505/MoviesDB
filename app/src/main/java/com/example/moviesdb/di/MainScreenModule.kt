package com.example.moviesdb.di

import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.DetailMovieApi
import com.example.moviesdb.data.repository.MainMovieRepositoryImpl
import com.example.moviesdb.domain.repository.MainMovieRepository
import com.example.moviesdb.domain.use_case.DeleteMovieFromList
import com.example.moviesdb.domain.use_case.GetMoviesFromWatchList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainScreenModule {

    @Provides
    @Singleton
    fun provideDeleteMovieFromList(repository: MainMovieRepository): DeleteMovieFromList {
        return DeleteMovieFromList(repository)
    }

    @Provides
    @Singleton
    fun provideGetMoviesFromWatchList(repository: MainMovieRepository): GetMoviesFromWatchList {
        return GetMoviesFromWatchList(repository)
    }

    @Provides
    @Singleton
    fun provideMainMovieRepository(
        api: DetailMovieApi,
        db: MoviesDbDatabase
    ): MainMovieRepository {
        return MainMovieRepositoryImpl(api, db)
    }
}