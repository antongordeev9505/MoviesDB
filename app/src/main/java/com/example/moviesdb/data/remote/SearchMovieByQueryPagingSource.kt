package com.example.moviesdb.data.remote

import androidx.paging.PagingSource
import com.example.moviesdb.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class SearchMovieByQueryPagingSource(private val query: String, private val api: MoviesDbApi) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.searchMoviesByQuery(position, query)
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