package com.example.moviesdb.di

import android.app.Application
import androidx.room.Room
import com.example.moviesdb.data.local.MoviesDbDatabase
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
    fun provideMoviesDatabase(app: Application): MoviesDbDatabase {
        return Room.databaseBuilder(app, MoviesDbDatabase::class.java, "movies_db")
            .build()
    }
}