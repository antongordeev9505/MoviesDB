package com.example.moviesdb.domain.use_case

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.DiscoverMoviesRepository
import kotlinx.coroutines.flow.Flow

class DiscoverMovies(private val repository: DiscoverMoviesRepository) {

    operator fun invoke(
        releaseYear: Int?,
        sortBy: String = "popularity.desc",
        minVoteCount: Int = 0,
        withGenre: String = "",
        voteAverage: Int = 0
    ): Flow<PagingData<Movie>> =
        repository.discoverMovies(releaseYear, sortBy, minVoteCount, withGenre, voteAverage)
}