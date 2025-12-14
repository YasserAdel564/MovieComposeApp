package com.example.composedemo.data.repository

import com.example.composedemo.data.remote.api.TMDBApiService
import com.example.composedemo.data.remote.dto.toDomain
import com.example.composedemo.domain.model.Movie
import com.example.composedemo.domain.model.MovieDetail
import com.example.composedemo.domain.repository.MovieRepository
import com.example.composedemo.util.NetworkResult
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TMDBApiService
) : MovieRepository {

    override suspend fun getPopularMovies(): NetworkResult<List<Movie>> {
        return try {
            val response = apiService.getPopularMovies()
            val movies = response.results.map { it.toDomain() }
            NetworkResult.Success(movies)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override suspend fun getTopRatedMovies(): NetworkResult<List<Movie>> {
        return try {
            val response = apiService.getTopRatedMovies()
            val movies = response.results.map { it.toDomain() }
            NetworkResult.Success(movies)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override suspend fun getUpcomingMovies(): NetworkResult<List<Movie>> {
        return try {
            val response = apiService.getUpcomingMovies()
            val movies = response.results.map { it.toDomain() }
            NetworkResult.Success(movies)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override suspend fun getNowPlayingMovies(): NetworkResult<List<Movie>> {
        return try {
            val response = apiService.getNowPlayingMovies()
            val movies = response.results.map { it.toDomain() }
            NetworkResult.Success(movies)
        } catch (e: Exception) {
            handleError(e)
        }
    }

    override suspend fun getMovieDetail(movieId: Int): NetworkResult<MovieDetail> {
        return try {
            val response = apiService.getMovieDetail(movieId)
            val movieDetail = response.toDomain()
            NetworkResult.Success(movieDetail)
        } catch (e: Exception) {
            handleError(e)
        }
    }


    private fun <T> handleError(e: Exception): NetworkResult<T> {
        return when (e) {
            is IOException -> NetworkResult.Error("Network error. Please check your connection.")
            is HttpException -> NetworkResult.Error("Server error: ${e.code()}")
            else -> NetworkResult.Error("Unknown error: ${e.localizedMessage}")
        }
    }
}
