package com.tynkovski.apps.messenger.core.designsystem.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.tynkovski.apps.messenger.core.designsystem.helpers.DisableMinimumTouch

@Composable
fun DefaultIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: IconButtonColors = IconButtonColors(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onBackground,
        disabledContainerColor = MaterialTheme.colorScheme.surface,
        disabledContentColor = MaterialTheme.colorScheme.outline,
    ),
    shape: Shape = CircleShape,
    ignoreMinimumTouch: Boolean = false,
    contentDescription: String? = null
) = DisableMinimumTouch(ignoreMinimumTouch) {
    FilledTonalIconButton(
        modifier = modifier,
        onClick = onClick,
        shape = shape, colors = colors
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun TransparentIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    ignoreMinimumTouch: Boolean = false,
    contentDescription: String? = null
) = DisableMinimumTouch(ignoreMinimumTouch) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            tint = iconTint,
            contentDescription = contentDescription
        )
    }
}
