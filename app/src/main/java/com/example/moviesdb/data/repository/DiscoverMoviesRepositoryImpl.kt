package com.example.moviesdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesdb.data.remote.DiscoverMoviesApi
import com.example.moviesdb.data.remote.pagination.DiscoverPagingSource
import com.example.moviesdb.domain.model.Genres
import com.example.moviesdb.domain.model.Movie

import com.example.moviesdb.domain.repository.DiscoverMoviesRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DiscoverMoviesRepositoryImpl(
    private val api: DiscoverMoviesApi
) : DiscoverMoviesRepository {

    override fun discoverMovies(
        releaseYear: Int?,
        sortBy: String,
        minVoteCount: Int,
        withGenre: String,
        voteAverage: Int
    ) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            DiscoverPagingSource(
                api,
                releaseYear,
                sortBy,
                minVoteCount,
                withGenre,
                voteAverage
            )
        }

    ).flow

    override fun getGenres(): Flow<Resource<Genres>> = flow {
        emit(Resource.Loading)

        try {
            val genres = api.getGenres().toListOfGenres()
            emit(Resource.Success(genres))

        } catch (error: HttpException) {
            emit(Resource.Error(exception = error.toString()))
        } catch (error: IOException) {
            emit(Resource.Error(exception = error.toString()))
        }
    }
}