package com.example.moviesdb.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movie")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movie")
    suspend fun getAllPopularMovies(): List<MovieEntity>

//    @Query("SELECT * FROM movie WHERE label LIKE :query")
//    fun pagingSource(query: String): PagingSource<Int, MovieEntity>
}