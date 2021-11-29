package com.example.moviesdb.data.remote

import com.example.moviesdb.data.remote.dto.PopularMoviesDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MoviesDbApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "e6054d9c27418f6c2422d14a6e4f1e20"
    }

    @GET("/movie/popular")
    suspend fun getPopularMovies(): PopularMoviesDto
}