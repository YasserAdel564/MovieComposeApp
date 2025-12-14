package com.example.composedemo.presentation.movielist

import app.cash.turbine.test
import com.example.composedemo.domain.model.Movie
import com.example.composedemo.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.composedemo.domain.usecase.GetPopularMoviesUseCase
import com.example.composedemo.domain.usecase.GetTopRatedMoviesUseCase
import com.example.composedemo.domain.usecase.GetUpcomingMoviesUseCase
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieListViewModelTest {

    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = mockk()
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase = mockk()
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase = mockk()
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase = mockk()
    
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: MovieListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        // Default mock behavior
        coEvery { getPopularMoviesUseCase() } returns NetworkResult.Success(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        viewModel = MovieListViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getNowPlayingMoviesUseCase
        )
        assertTrue(viewModel.uiState.value is MovieListUiState.Loading)
    }

    @Test
    fun `loadMovies success updates uiState to Success`() = runTest {
        val movies = listOf(
            Movie(1, "Test Movie", "Desc", "path", "back", "2023", 8.0)
        )
        coEvery { getPopularMoviesUseCase() } returns NetworkResult.Success(movies)

        viewModel = MovieListViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getNowPlayingMoviesUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val item = awaitItem()
            assertTrue(item is MovieListUiState.Success)
            assertEquals(movies, (item as MovieListUiState.Success).movies)
        }
    }
    
    @Test
    fun `loadMovies error updates uiState to Error`() = runTest {
        val errorMessage = "Network Error"
        coEvery { getPopularMoviesUseCase() } returns NetworkResult.Error(errorMessage)

        viewModel = MovieListViewModel(
            getPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            getUpcomingMoviesUseCase,
            getNowPlayingMoviesUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
             val item = awaitItem()
             assertTrue(item is MovieListUiState.Error)
             assertEquals(errorMessage, (item as MovieListUiState.Error).message)
        }
    }
}
