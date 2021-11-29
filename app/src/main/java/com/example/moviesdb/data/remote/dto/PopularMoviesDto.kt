package com.example.moviesdb.data.remote.dto

data class PopularMoviesDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)