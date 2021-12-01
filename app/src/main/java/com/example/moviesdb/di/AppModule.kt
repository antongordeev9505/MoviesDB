package com.example.moviesdb.di

import android.app.Application
import androidx.room.Room
import com.example.moviesdb.data.local.MoviesDbDatabase
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
object AppModule {

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(repository: PopularSearchMoviesRepository): GetPopularMovies {
        return GetPopularMovies(repository)
    }

    @Provides
    @Singleton
    fun provideSearchMoviesByQuery(repository: PopularSearchMoviesRepository): SearchMoviesByQuery {
        return SearchMoviesByQuery(repository)
    }

    @Provides
    @Singleton
    fun providePopularSearchMoviesRepository(
        api: MoviesDbApi
    ): PopularSearchMoviesRepository {
        return PopularSearchMoviesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDbDatabase {
        return Room.databaseBuilder(app, MoviesDbDatabase::class.java, "movies_db")
            .build()
    }
}