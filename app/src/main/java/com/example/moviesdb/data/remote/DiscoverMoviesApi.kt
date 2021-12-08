package com.example.moviesdb.data.remote

import com.example.moviesdb.data.remote.dto.ListMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMoviesApi {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("with_cast") withCast: String
    ): ListMoviesDto
}