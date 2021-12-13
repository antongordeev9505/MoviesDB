package com.example.moviesdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class,
        CustomListEntity::class],
    version = 1
)
abstract class MoviesDbDatabase : RoomDatabase() {

    abstract val dao: MoviesDbDao
    abstract val daoCustomList: CustomListDao
}