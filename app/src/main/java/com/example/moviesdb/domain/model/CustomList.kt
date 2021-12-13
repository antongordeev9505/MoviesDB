package com.example.moviesdb.domain.model

import com.example.moviesdb.data.local.CustomListEntity

data class CustomList(
    val idList: Int,
    val title: String
) {
    fun toCustomListEntity(): CustomListEntity {
        return CustomListEntity(
            idList = idList,
            title = title
        )
    }
}