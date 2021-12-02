package com.example.moviesdb.domain.model

data class CastByMovie(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
) {
    data class Cast(
        val cast_id: Int,
        val character: String,
        val credit_id: String,
        val id: Int,
        val name: String,
        val order: Int,
        val profile_path: String?
    )

    data class Crew(
        val department: String,
        val job: String,
        val name: String,
    )
}