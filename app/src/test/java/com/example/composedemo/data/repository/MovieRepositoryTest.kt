package com.example.composedemo.data.repository

import com.example.composedemo.data.remote.api.TMDBApiService
import com.example.composedemo.data.remote.dto.MovieDto
import com.example.composedemo.data.remote.dto.MovieResponse
import com.example.composedemo.util.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class MovieRepositoryTest {

    private val apiService: TMDBApiService = mockk()

    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setUp() {
        repository = MovieRepositoryImpl(apiService)
    }

    @Test
    fun `getPopularMovies returns Success when API call is successful`() = runBlocking {
        val movieDto = MovieDto(1, "Title", "Overview", "path", "back", "date", 8.0)
        val response = MovieResponse(listOf(movieDto))
        
        coEvery { apiService.getPopularMovies() } returns response

        val result = repository.getPopularMovies()

        assertTrue(result is NetworkResult.Success)
        assertTrue((result as NetworkResult.Success).data.isNotEmpty())
        assertTrue(result.data[0].title == "Title")
    }

    @Test
    fun `getPopularMovies returns Error when API call fails`() = runBlocking {
        coEvery { apiService.getPopularMovies() } throws IOException("Network Failure")

        val result = repository.getPopularMovies()

        assertTrue(result is NetworkResult.Error)
    }
}
