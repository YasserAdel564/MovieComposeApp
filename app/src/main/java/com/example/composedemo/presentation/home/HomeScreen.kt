package com.example.composedemo.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composedemo.domain.model.Movie
import com.example.composedemo.presentation.components.ErrorView
import com.example.composedemo.presentation.home.components.MovieRow

@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        onMovieClick = onMovieClick,
        onRetry = { viewModel.retry() }
    )
}

@Composable
fun HomeContent(
    uiState: HomeState,
    onMovieClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingState()
                }
                uiState.error != null -> {
                    ErrorView(
                        message = uiState.error,
                        onRetry = onRetry
                    )
                }
                else -> {
                    HomeContentBody(
                        uiState = uiState,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun HomeContentBody(
    uiState: HomeState,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        if (uiState.popularMovies.isNotEmpty()) {
            MovieRow(
                title = "Popular on Netflix",
                movies = uiState.popularMovies,
                onMovieClick = onMovieClick
            )
        }
        if (uiState.topRatedMovies.isNotEmpty()) {
            MovieRow(
                title = "Top Rated",
                movies = uiState.topRatedMovies,
                onMovieClick = onMovieClick
            )
        }
        if (uiState.upcomingMovies.isNotEmpty()) {
            MovieRow(
                title = "Upcoming",
                movies = uiState.upcomingMovies,
                onMovieClick = onMovieClick
            )
        }
        if (uiState.nowPlayingMovies.isNotEmpty()) {
            MovieRow(
                title = "Now Playing",
                movies = uiState.nowPlayingMovies,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeState(
                isLoading = false,
                popularMovies = listOf(
                    Movie(1, "Movie 1", "Overview", null, null, "2023", 8.0),
                    Movie(2, "Movie 2", "Overview", null, null, "2023", 7.5)
                ),
                topRatedMovies = emptyList(),
                upcomingMovies = emptyList(),
                nowPlayingMovies = emptyList()
            ),
            onMovieClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun LoadingStatePreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeState(isLoading = true),
            onMovieClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun ErrorStatePreview() {
    MaterialTheme {
        HomeContent(
            uiState = HomeState(error = "Network Error"),
            onMovieClick = {},
            onRetry = {}
        )
    }
}
