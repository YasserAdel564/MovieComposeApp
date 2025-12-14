package com.example.composedemo.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBar(
    rating: Double,
    modifier: Modifier = Modifier,
    stars: Int = 5,
    starsColor: Color = Color(0xFFFFC107)
) {
    val filledStars = kotlin.math.floor(rating).toInt()
    val unfilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfStar = rating.rem(1) != 0.0

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = starsColor)
        }

        if (halfStar) {
            Icon(imageVector = Icons.Default.StarHalf, contentDescription = null, tint = starsColor)
        }

        repeat(unfilledStars) {
            Icon(imageVector = Icons.Default.StarBorder, contentDescription = null, tint = starsColor)
        }
    }
}
