package com.example.moviesdb.data.remote

import com.example.moviesdb.data.remote.dto.PopularMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): PopularMoviesDto
}