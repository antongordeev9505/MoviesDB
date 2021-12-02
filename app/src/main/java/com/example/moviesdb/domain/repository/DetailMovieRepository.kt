package com.example.moviesdb.domain.repository

import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow

interface DetailMovieRepository {

     fun getRecommendations(movieId: Int): Flow<Resource<List<Movie>>>
}