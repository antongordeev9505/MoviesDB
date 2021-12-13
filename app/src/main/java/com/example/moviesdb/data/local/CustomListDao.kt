package com.example.moviesdb.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertListItem(listItem: CustomListEntity)

    @Query("SELECT * FROM list")
    fun getAllListItems(): Flow<List<CustomListEntity>>
}