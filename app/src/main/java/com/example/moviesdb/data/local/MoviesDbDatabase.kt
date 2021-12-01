package com.example.moviesdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class, RemoteKeys::class],
    version = 1
)
abstract class MoviesDbDatabase: RoomDatabase() {

    abstract val dao: MoviesDbDao
    abstract val daoKeys: RemoteKeysDao

//    companion object {
//
//        @Volatile
//        private var INSTANCE: MoviesDbDatabase? = null
//
//        fun getInstance(context: Context): MoviesDbDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE
//                    ?: buildDatabase(context).also { INSTANCE = it }
//            }
//    }
}