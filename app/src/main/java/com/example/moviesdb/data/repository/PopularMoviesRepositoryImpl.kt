package com.example.moviesdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.data.remote.PagingSource
import com.example.moviesdb.domain.repository.PopularMoviesRepository

class PopularMoviesRepositoryImpl(
    private val api: MoviesDbApi
) : PopularMoviesRepository {

    override fun getAllPopularMovies() =

        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingSource(api) }

        ).flow
}