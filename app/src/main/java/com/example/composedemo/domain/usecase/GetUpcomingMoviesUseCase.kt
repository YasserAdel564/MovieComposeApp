package com.example.composedemo.domain.usecase

import com.example.composedemo.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() = repository.getUpcomingMovies()
}
