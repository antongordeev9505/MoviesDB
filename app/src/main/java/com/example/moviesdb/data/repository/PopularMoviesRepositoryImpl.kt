package com.example.moviesdb.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.domain.repository.PopularMoviesRepository
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class PopularMoviesRepositoryImpl(
    private val api: MoviesDbApi,
    private val db: MoviesDbDatabase
) : PopularMoviesRepository {

    private val dao = db.dao

    override fun getAllPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        val popularMovies = dao.getAllPopularMovies().map { it.toMovie() }
        emit(Resource.Loading(data = popularMovies))

        try {
            val remotePopularMovies = api.getPopularMovies()

            db.withTransaction {
                dao.deleteAllMovies()
                dao.insertMovies(remotePopularMovies.results.map {
                    it.toMovieEntity()
                })
            }

            val newPopularMovies = dao.getAllPopularMovies().map {
                it.toMovie()
            }
            emit(Resource.Success(newPopularMovies))

        } catch (error: HttpException) {
            emit(Resource.Error(
                message = error.toString(),
                data = popularMovies
            ))

        } catch (error: IOException) {
            emit(Resource.Error(
                message = error.toString(),
                data = popularMovies
            ))
        }
    }
}