package com.example.moviesdb.data.remote

import com.example.moviesdb.data.remote.dto.GenresDto
import com.example.moviesdb.data.remote.dto.ListMoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMoviesApi {

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") page: Int,
        @Query("primary_release_year") releaseYear: Int?,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") minVoteCount: Int,
        @Query("with_genres") withGenre: String,
        @Query("vote_average.gte") voteAverage: Int
    ): ListMoviesDto

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresDto
}