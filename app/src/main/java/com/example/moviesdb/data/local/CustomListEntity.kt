package com.example.moviesdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviesdb.domain.model.CustomList

@Entity(tableName = "list")
data class CustomListEntity(
    @PrimaryKey
    val idList: Int,
    val title: String
) {
    fun toCustomList(): CustomList {
        return CustomList(
            idList = idList,
            title = title
        )
    }
}