package com.example.moviesdb.data.remote.dto

import com.example.moviesdb.domain.model.CastByMovie

data class CastByMovieDto(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
) {

    fun toCastByMovie(): CastByMovie {
        return CastByMovie(
            cast = cast.map { it.toCastByMovie() },
            crew = crew.map { it.toCastCrewByMovie() },
            id = id
        )
    }
    data class Cast(
        val cast_id: Int,
        val character: String,
        val credit_id: String,
        val id: Int,
        val name: String,
        val order: Int,
        val profile_path: String?
    ) {
        fun toCastByMovie(): CastByMovie.Cast {
            return CastByMovie.Cast(
                cast_id = cast_id,
                character = character,
                credit_id = credit_id,
                id = id,
                name = name,
                order = order,
                profile_path = profile_path
            )
        }
    }

    data class Crew(
        val department: String,
        val job: String,
        val name: String,
    ) {
        fun toCastCrewByMovie(): CastByMovie.Crew {
            return CastByMovie.Crew(
                department = department,
                job = job,
                name = name
            )
        }
    }
}