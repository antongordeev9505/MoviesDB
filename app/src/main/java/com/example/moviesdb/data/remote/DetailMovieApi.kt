package com.example.moviesdb.data.remote

import com.example.moviesdb.data.remote.dto.CastByMovieDto
import com.example.moviesdb.data.remote.dto.ImagesByMovieDto
import com.example.moviesdb.data.remote.dto.ListMoviesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailMovieApi {

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendation(
        @Path("movie_id") movieId: Int
    ): ListMoviesDto

    @GET("movie/{movie_id}/images")
    suspend fun getImagesByMovie(
        @Path("movie_id") movieId: Int,
        @Query("include_image_language") language: String
    ): ImagesByMovieDto

    @GET("movie/{movie_id}/credits")
    suspend fun getCastByMovie(
        @Path("movie_id") movieId: Int
    ): CastByMovieDto
}