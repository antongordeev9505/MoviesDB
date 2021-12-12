package com.example.moviesdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovie(id: Int)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM movie WHERE id = :id)")
    fun existItem(id: Int): Flow<Boolean>
}