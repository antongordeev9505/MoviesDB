package com.example.moviesdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesdb.data.remote.DiscoverMoviesApi
import com.example.moviesdb.data.remote.pagination.DiscoverPagingSource

import com.example.moviesdb.domain.repository.DiscoverMoviesRepository

class DiscoverMoviesRepositoryImpl(
    private val api: DiscoverMoviesApi
) : DiscoverMoviesRepository {

    override fun discoverMovies(withCast: String, sortBy: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { DiscoverPagingSource(withCast, sortBy, api) }

    ).flow
}