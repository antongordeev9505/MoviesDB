package com.example.moviesdb.di

import android.app.Application
import androidx.room.Room
import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.DetailMovieApi
import com.example.moviesdb.data.remote.DiscoverMoviesApi
import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.data.repository.DetailMovieRepositoryImpl
import com.example.moviesdb.data.repository.DiscoverMoviesRepositoryImpl
import com.example.moviesdb.data.repository.PopularSearchMoviesRepositoryImpl
import com.example.moviesdb.domain.repository.DetailMovieRepository
import com.example.moviesdb.domain.repository.DiscoverMoviesRepository
import com.example.moviesdb.domain.repository.PopularSearchMoviesRepository
import com.example.moviesdb.domain.use_case.*
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
    fun providePopularSearchMoviesRepository(
        api: MoviesDbApi
    ): PopularSearchMoviesRepository {
        return PopularSearchMoviesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDetailMovieRepository(
        api: DetailMovieApi
    ): DetailMovieRepository {
        return DetailMovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDbDatabase {
        return Room.databaseBuilder(app, MoviesDbDatabase::class.java, "movies_db")
            .build()
    }
}