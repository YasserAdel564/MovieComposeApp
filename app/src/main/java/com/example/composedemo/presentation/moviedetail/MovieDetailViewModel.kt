package com.example.composedemo.presentation.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedemo.domain.usecase.GetMovieDetailUseCase
import com.example.composedemo.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    init {
        loadMovieDetail()
    }

    fun retry() {
        loadMovieDetail()
    }

    private fun loadMovieDetail() {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            when (val result = getMovieDetailUseCase(movieId)) {
                is NetworkResult.Success -> {
                    _uiState.value = MovieDetailUiState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = MovieDetailUiState.Error(result.message)
                }
                is NetworkResult.Loading -> {
                     _uiState.value = MovieDetailUiState.Loading
                }
            }
        }
    }
}
