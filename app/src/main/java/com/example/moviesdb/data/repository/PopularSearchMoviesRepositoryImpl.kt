package com.example.moviesdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.data.remote.PopularPagingSource
import com.example.moviesdb.data.remote.SearchMovieByQueryPagingSource
import com.example.moviesdb.domain.repository.PopularSearchMoviesRepository

class PopularSearchMoviesRepositoryImpl(
    private val api: MoviesDbApi
) : PopularSearchMoviesRepository {

    override fun getAllPopularMovies() = Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PopularPagingSource(api) }

        ).flow

    override fun searchMoviesByQuery(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearchMovieByQueryPagingSource(query, api) }

    ).flow
}