package com.example.composedemo.presentation.moviedetail

import com.example.composedemo.domain.model.MovieDetail

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(val movie: MovieDetail) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}
