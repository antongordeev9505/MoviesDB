package com.example.moviesdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MoviesDbDatabase: RoomDatabase() {

    abstract val dao: MoviesDbDao

}