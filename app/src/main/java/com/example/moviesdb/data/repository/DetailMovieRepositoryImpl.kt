package com.example.moviesdb.data.repository

import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.DetailMovieApi
import com.example.moviesdb.data.remote.dto.CastByMovieDto
import com.example.moviesdb.domain.model.CastByMovie
import com.example.moviesdb.domain.model.ImagesByMovie
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.model.MovieDetails
import com.example.moviesdb.domain.repository.DetailMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

private const val LANGUAGE = "en,null"

class DetailMovieRepositoryImpl(
    private val api: DetailMovieApi,
    private val db: MoviesDbDatabase
) : DetailMovieRepository {

    private val dao = db.dao

    override fun getRecommendations(
        movieId: Int
    ): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)

        try {
            val recommendations = api.getRecommendation(movieId).results.map {
                it.toMovie()
            }

            emit(Resource.Success(recommendations))
        } catch (error: HttpException) {
            emit(Resource.Error(exception = error.toString()))

        } catch (error: IOException) {
            emit(Resource.Error(exception = error.toString()))
        }
    }

    override fun getImagesByMovie(
        movieId: Int
    ): Flow<Resource<ImagesByMovie>> = flow {
        emit(Resource.Loading)

        try {
            val images = api.getImagesByMovie(movieId, LANGUAGE).toImagesByMovie()

            emit(Resource.Success(images))
        } catch (error: HttpException) {
            emit(Resource.Error(exception = error.toString()))

        } catch (error: IOException) {
            emit(Resource.Error(exception = error.toString()))
        }
    }

    override fun getCastByMovie(
        movieId: Int
    ): Flow<Resource<CastByMovie>> = flow {
        emit(Resource.Loading)

        try {
            val response = api.getCastByMovie(movieId)

            val cast = response.copy().cast.filter {
                it.order < 10
            }

            val crew = response.copy().crew.filter {
                it.department == "Directing"
            }.filter {
                it.job == "Director"
            }

            val castCrew = CastByMovieDto(cast, crew, response.id).toCastByMovie()

            emit(Resource.Success(castCrew))
        } catch (error: HttpException) {
            emit(Resource.Error(exception = error.toString()))

        } catch (error: IOException) {
            emit(Resource.Error(exception = error.toString()))
        }
    }

    override fun getMovieDetails(movieId: Int): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading)

        try {
            val movieDetails = api.getMovieDetails(movieId).toMovieDetails()

            emit(Resource.Success(movieDetails))
        } catch (error: HttpException) {
            emit(Resource.Error(exception = error.toString()))

        } catch (error: IOException) {
            emit(Resource.Error(exception = error.toString()))
        }
    }

    override suspend fun checkMovieInWatchList(movieId: Int): Flow<Resource<Boolean>> {
        return try {
            val result = dao.existItem(movieId).map {
                Resource.Success(it)
            }
            result
        } catch (error: Exception) {
            flow {
                Resource.Error(error.toString())
            }
        }
    }
}