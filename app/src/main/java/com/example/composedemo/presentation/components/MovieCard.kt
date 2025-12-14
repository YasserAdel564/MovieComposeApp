package com.example.composedemo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.composedemo.domain.model.Movie
import com.example.composedemo.util.Constants

/**
 * A Card component representing a single movie item.
 *
 * @param movie The movie data to display.
 * @param onMovieClick Callback invoked when the card is clicked, passing the movie ID.
 * @param modifier Modifier to be applied to the card.
 */
@Composable
fun MovieCard(
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) },
        shape = RoundedCornerShape(12.dp)
    ) {
        AsyncImage(
            model = Constants.IMAGE_BASE_URL + movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}
