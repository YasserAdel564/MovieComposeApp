package com.example.composedemo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.domain.usecase.GetNowPlayingMoviesUseCase
import com.example.composedemo.domain.usecase.GetPopularMoviesUseCase
import com.example.composedemo.domain.usecase.GetTopRatedMoviesUseCase
import com.example.composedemo.domain.usecase.GetUpcomingMoviesUseCase
import com.example.composedemo.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadAllMovies()
    }

    private fun loadAllMovies() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val popularResult = getPopularMoviesUseCase()
            val topRatedResult = getTopRatedMoviesUseCase()
            val upcomingResult = getUpcomingMoviesUseCase()
            val nowPlayingResult = getNowPlayingMoviesUseCase()

            val hasError = popularResult is NetworkResult.Error ||
                    topRatedResult is NetworkResult.Error ||
                    upcomingResult is NetworkResult.Error ||
                    nowPlayingResult is NetworkResult.Error

            if (hasError) {
                val errorMessage = (popularResult as? NetworkResult.Error)?.message
                    ?: (topRatedResult as? NetworkResult.Error)?.message
                    ?: (upcomingResult as? NetworkResult.Error)?.message
                    ?: (nowPlayingResult as? NetworkResult.Error)?.message
                    ?: "An unknown error occurred"
                _uiState.update { it.copy(isLoading = false, error = errorMessage) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        popularMovies = (popularResult as NetworkResult.Success).data,
                        topRatedMovies = (topRatedResult as NetworkResult.Success).data,
                        upcomingMovies = (upcomingResult as NetworkResult.Success).data,
                        nowPlayingMovies = (nowPlayingResult as NetworkResult.Success).data,
                        error = null
                    )
                }
            }
        }
    }

    fun retry() {
        loadAllMovies()
    }
}
