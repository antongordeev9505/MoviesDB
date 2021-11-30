package com.example.moviesdb.data.remote

import com.example.moviesdb.data.remote.dto.PopularMoviesDto
import retrofit2.http.GET

interface MoviesDbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(): PopularMoviesDto
}