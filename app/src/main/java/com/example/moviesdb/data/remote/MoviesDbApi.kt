package com.example.moviesdb.data.remote

import com.example.moviesdb.data.remote.dto.ListMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): ListMoviesDto

    @GET("search/movie")
    suspend fun searchMoviesByQuery(
        @Query("page") page: Int, @Query("query") query: String
    ): ListMoviesDto
}