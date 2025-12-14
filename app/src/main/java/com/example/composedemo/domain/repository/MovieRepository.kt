package com.example.composedemo.domain.repository

import com.example.composedemo.domain.model.Movie
import com.example.composedemo.domain.model.MovieDetail
import com.example.composedemo.util.NetworkResult

interface MovieRepository {
    suspend fun getPopularMovies(): NetworkResult<List<Movie>>
    suspend fun getTopRatedMovies(): NetworkResult<List<Movie>>
    suspend fun  getUpcomingMovies(): NetworkResult<List<Movie>>
    suspend fun getNowPlayingMovies(): NetworkResult<List<Movie>>
    suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetail>
}
