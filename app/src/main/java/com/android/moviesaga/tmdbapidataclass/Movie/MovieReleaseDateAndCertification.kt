package com.example.moviesaga.tmdbapidataclass.Movie

data class MovieReleaseDateAndCertification(
    val id: Int,
    val results: List<Result>
)
data class Result(
    val iso_3166_1: String,
    val release_dates: List<ReleaseDate>
)

data class ReleaseDate(
    val certification: String,
    val descriptors: List<String>,
    val iso_639_1: String,
    val note: String,
    val release_date: String,
    val type: Int
)