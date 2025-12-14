package com.example.composedemo.data.remote.dto

import com.example.composedemo.domain.model.Genre
import com.example.composedemo.domain.model.MovieDetail
import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    val runtime: Int,
    val genres: List<GenreDto>,
    val tagline: String?
)

data class GenreDto(
    val id: Int,
    val name: String
)

fun MovieDetailDto.toDomain() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    runtime = runtime,
    genres = genres.map { Genre(it.id, it.name) },
    tagline = tagline
)
