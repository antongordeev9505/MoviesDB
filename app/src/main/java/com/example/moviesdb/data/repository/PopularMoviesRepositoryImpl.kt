package com.example.moviesdb.data.repository

import android.util.Log
import com.example.moviesdb.data.local.MoviesDbDao
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
    private val dao: MoviesDbDao
) : PopularMoviesRepository {

//    private val moviesDao = db.dao

    override fun getAllPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading())

        val popularMovies = dao.getAllPopularMovies().map { it.toMovie() }
        emit(Resource.Loading(data = popularMovies))
        Log.d("proverka", popularMovies.toString())
        val remotePopularMovies = api.getPopularMovies()


        try {
//            val remotePopularMovies = api.getPopularMovies()
//            db.withTransaction {
//                moviesDao.deleteAllMovies()
//                moviesDao.insertMovies(remotePopularMovies.results.map {
//                    it.toMovieEntity()
//                })
//            }
            Log.d("proverka", "delete and insert")

            dao.deleteAllMovies()
            dao.insertMovies(remotePopularMovies.results.map {
                it.toMovieEntity()
            })

        } catch (error: HttpException) {
            Log.d("proverka", "HttpException")

            emit(Resource.Error(
                message = error.toString(),
                data = popularMovies
            ))

        } catch (error: IOException) {
            Log.d("proverka", "IOException")

            emit(Resource.Error(
                message = error.toString(),
                data = popularMovies
            ))
        }

        val newPopularMovies = dao.getAllPopularMovies().map {
            it.toMovie()
        }
        emit(Resource.Success(newPopularMovies))
    }
}