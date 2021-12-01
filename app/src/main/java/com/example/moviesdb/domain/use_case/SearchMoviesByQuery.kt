package com.example.moviesdb.domain.use_case

import androidx.paging.PagingData
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.PopularSearchMoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesByQuery(private val repositorySearch: PopularSearchMoviesRepository) {

    operator fun invoke(query: String): Flow<PagingData<Movie>> =
        repositorySearch.searchMoviesByQuery(query)
}