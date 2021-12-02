package com.example.moviesdb.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val original_title: String?,
    val poster_path: String?,
    val overview: String?
): Parcelable
