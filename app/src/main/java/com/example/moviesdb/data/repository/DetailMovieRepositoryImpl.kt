package com.example.moviesdb.data.repository

import com.example.moviesdb.data.remote.DetailMovieApi
import com.example.moviesdb.domain.model.ImageByMovie
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.repository.DetailMovieRepository
import com.example.moviesdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

private const val LANGUAGE = "en,null"

class DetailMovieRepositoryImpl(
    private val api: DetailMovieApi
) : DetailMovieRepository {

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
    ): Flow<Resource<List<ImageByMovie>>> = flow {
        emit(Resource.Loading)

        try {
            val images = api.getImagesByMovie(movieId, LANGUAGE).backdrops.map {
                it.toImageByMovie()
            }

            emit(Resource.Success(images))
        } catch (error: HttpException) {
            emit(Resource.Error(exception = error.toString()))

        } catch (error: IOException) {
            emit(Resource.Error(exception = error.toString()))
        }
    }
}