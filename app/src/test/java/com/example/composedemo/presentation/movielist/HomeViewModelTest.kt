package com.example.composedemo.presentation.movielist

import app.cash.turbine.test
import com.example.composedemo.domain.model.Movie
import com.example.composedemo.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.composedemo.domain.usecase.GetPopularMoviesUseCase
import com.example.composedemo.domain.usecase.GetTopRatedMoviesUseCase
import com.example.composedemo.domain.usecase.GetUpcomingMoviesUseCase
import com.example.composedemo.presentation.home.HomeViewModel
import com.example.composedemo.util.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = mockk()
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase = mockk()
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        // Default mock behavior for all use cases
        coEvery { getPopularMoviesUseCase() } returns NetworkResult.Success(emptyList())
        coEvery { getTopRatedMoviesUseCase() } returns NetworkResult.Success(emptyList())
        coEvery { getUpcomingMoviesUseCase() } returns NetworkResult.Success(emptyList())
        coEvery { getNowPlayingMoviesUseCase() } returns NetworkResult.Success(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when ViewModel is created, uiState is loading`() = runTest {
        // When
        viewModel = HomeViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getNowPlayingMoviesUseCase
        )
        // Then
        Assert.assertTrue(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `when all use cases succeed, uiState is updated with movie lists`() = runTest {
        // Given
        val popularMovies = listOf(Movie(1, "Popular", "Desc", "path", "back", "2023", 8.0))
        val topRatedMovies = listOf(Movie(2, "Top Rated", "Desc", "path", "back", "2023", 9.0))
        coEvery { getPopularMoviesUseCase() } returns NetworkResult.Success(popularMovies)
        coEvery { getTopRatedMoviesUseCase() } returns NetworkResult.Success(topRatedMovies)

        // When
        viewModel = HomeViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getNowPlayingMoviesUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            Assert.assertEquals(false, state.isLoading)
            Assert.assertEquals(popularMovies, state.popularMovies)
            Assert.assertEquals(topRatedMovies, state.topRatedMovies)
            Assert.assertEquals(emptyList<Movie>(), state.upcomingMovies)
            Assert.assertEquals(emptyList<Movie>(), state.nowPlayingMovies)
            Assert.assertNull(state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when one use case fails, uiState is updated with error`() = runTest {
        // Given
        val errorMessage = "Network Error"
        coEvery { getPopularMoviesUseCase() } returns NetworkResult.Error(errorMessage)

        // When
        viewModel = HomeViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getNowPlayingMoviesUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            Assert.assertEquals(false, state.isLoading)
            Assert.assertEquals(errorMessage, state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}