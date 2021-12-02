package com.example.moviesdb.domain.repository

import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.model.ImageByMovie
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {

    fun getRecommendations(movieId: Int): Flow<Resource<List<Movie>>>

    fun getImagesByMovie(movieId: Int): Flow<Resource<List<ImageByMovie>>>

    fun getCastByMovie(movieId: Int): Flow<Resource<CastByMovie>>
}