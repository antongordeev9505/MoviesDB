package com.example.moviesdb.data.repository

import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.DetailMovieApi
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.MainMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MainMovieRepositoryImpl(
    private val api: DetailMovieApi,
    private val db: MoviesDbDatabase
) : MainMovieRepository {

    private val dao = db.dao

    override suspend fun insertMovie(movieId: Int): Flow<Resource<String>> = flow {
        try {
            val movie = api.getMovieDetails(movieId).toMovieEntity()
            dao.insertMovie(movie)

            emit(Resource.Success("${movie.original_title} added"))

        } catch (error: Throwable) {
            emit(Resource.Error("It`s a problem..."))
        }
    }

    override suspend fun getAllMovies(): Flow<Resource<List<Movie>>> {
        try {
            val movies = dao.getAllMovies().map {
                it.map { movieEntity ->
                    movieEntity.toMovie()
                }
            }.map {
                Resource.Success(it)
            }.flowOn(Dispatchers.Default)

            return movies

        } catch (error: Throwable) {
            return flow {
                Resource.Error(error.toString())
            }
        }
    }
}