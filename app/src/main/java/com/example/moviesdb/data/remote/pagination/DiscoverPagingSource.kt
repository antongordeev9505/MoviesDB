package com.example.moviesdb.data.remote.pagination

import androidx.paging.PagingSource
import com.example.moviesdb.data.remote.DiscoverMoviesApi
import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class DiscoverPagingSource(private val withCast: String, private val api: DiscoverMoviesApi) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.discoverMovies(position, withCast)
            val movies = response.results.map { it.toMovie() }

            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (error: IOException) {
            LoadResult.Error(error)

        } catch (error: HttpException) {
            LoadResult.Error(error)
        }
    }
}