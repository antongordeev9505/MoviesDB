package com.example.moviesdb.domain.repository

import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.model.ImagesByMovie
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {

    fun getRecommendations(movieId: Int): Flow<Resource<List<Movie>>>

    fun getImagesByMovie(movieId: Int): Flow<Resource<ImagesByMovie>>

    fun getCastByMovie(movieId: Int): Flow<Resource<CastByMovie>>

    fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>>

    suspend fun checkMovieInWatchList(movieId: Int): Flow<Resource<Boolean>>
}