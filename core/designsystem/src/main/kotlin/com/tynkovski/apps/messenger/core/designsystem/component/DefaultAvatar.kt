package com.tynkovski.apps.messenger.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.tynkovski.apps.messenger.core.designsystem.R

@Composable
fun DefaultAvatar(
    url: String?,
    modifier: Modifier = Modifier,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = url,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    Image(
        modifier = modifier
            .size(48.dp)
            .background(shape = CircleShape, color = MaterialTheme.colorScheme.outline),
        contentScale = ContentScale.Inside,
        painter = if (isError.not()) {
            imageLoader
        } else {
            painterResource(R.drawable.core_designsystem_ic_people)
        },
        contentDescription = null,
    )
}