package com.example.moviesdb.di

import android.app.Application
import android.app.appsearch.GetByDocumentIdRequest
import androidx.room.Room
import com.example.moviesdb.data.local.MoviesDbDatabase
import com.example.moviesdb.data.remote.MoviesDbApi
import com.example.moviesdb.data.repository.PopularMoviesRepositoryImpl
import com.example.moviesdb.domain.repository.PopularMoviesRepository
import com.example.moviesdb.domain.use_case.GetPopularMovies
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val TMDb_API_KEY = "e6054d9c27418f6c2422d14a6e4f1e20"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .addInterceptor(provideInterceptor())
            .build()

    //    @Provides
//    @Singleton
    private fun provideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("api_key", MoviesDbApi.API_KEY)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }



//    private fun buildAuthorizationInterceptor() = object : Interceptor {
//
//        private var credentials: String = Credentials.basic(login, password)
//
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val request = chain.request()
//
//            val new = request.newBuilder()
//                .addHeader("Authorization", credentials)
//                .build()
//            return chain.proceed(new)
//        }
//    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MoviesDbApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit) : MoviesDbApi =
        retrofit.create(MoviesDbApi::class.java)


    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(repository: PopularMoviesRepository): GetPopularMovies {
        return GetPopularMovies(repository)
    }

    @Provides
    @Singleton
    fun providePopularMoviesRepository(
        db: MoviesDbDatabase,
        api: MoviesDbApi
    ): PopularMoviesRepository {
        return PopularMoviesRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(app: Application): MoviesDbDatabase {
        return Room.databaseBuilder(app, MoviesDbDatabase::class.java, "movies_db")
            .build()
    }

    //remote part
//    @Provides
//    @Singleton
//    fun provideClient(authInterceptor: Interceptor): OkHttpClient =
//        OkHttpClient().newBuilder()
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .addInterceptor(authInterceptor)
//            .build()

//    @Provides
//    @Singleton
//    fun provideAuthorizationInterceptor(): Interceptor {
//        return Interceptor { chain ->
//            val newUrl = chain.request().url
//                .newBuilder()
//                .addQueryParameter("api_key", TMDb_API_KEY)
//                .build()
//
//            val newRequest = chain.request()
//                .newBuilder()
//                .url(newUrl)
//                .build()
//
//            chain.proceed(newRequest)
//        }
//    }





}