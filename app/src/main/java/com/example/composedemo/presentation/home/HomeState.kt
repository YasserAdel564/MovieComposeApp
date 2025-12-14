package com.example.composedemo.presentation.home

import com.example.composedemo.domain.model.Movie

data class HomeState(
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
