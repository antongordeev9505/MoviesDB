package com.example.moviesdb.di

import android.app.Application
import androidx.room.Room
import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.data.repository.PopularMoviesRepositoryImpl
import com.example.moviesdb.domain.repository.PopularMoviesRepository
import com.example.moviesdb.domain.use_case.GetPopularMovies
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
    fun provideGetPopularMoviesUseCase(repository: PopularMoviesRepository): GetPopularMovies {
        return GetPopularMovies(repository)
    }

    @Provides
    @Singleton
    fun providePopularMoviesRepository(
        db: MoviesDbDatabase,
        api: MoviesDbApi
    ): PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDbDatabase {
        return Room.databaseBuilder(app, MoviesDbDatabase::class.java, "movies_db")
            .build()
    }
}