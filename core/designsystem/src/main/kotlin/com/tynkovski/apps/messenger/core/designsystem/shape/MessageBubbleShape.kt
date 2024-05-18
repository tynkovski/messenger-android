package com.tynkovski.apps.messenger.core.designsystem.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.tynkovski.apps.messenger.core.designsystem.path.MessageBubblePath

enum class TailPosition {
    Right, Left
}

class MessageBubbleShape(
    private val tailPosition: TailPosition,
    private val showTail: Boolean,
    private val corners: Dp,
    private val arrowWidth: Dp,
    private val arrowHeight: Dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ) = Outline.Generic(
        path = when(tailPosition) {
            TailPosition.Right -> MessageBubblePath.drawBubbleRightPath(
                size = size,
                density = density,
                showTail = showTail,
                corners = corners,
                arrowWidth = arrowWidth,
                arrowHeight = arrowHeight,
            )
            TailPosition.Left -> MessageBubblePath.drawBubbleLeftPath(
                size = size,
                density = density,
                showTail = showTail,
                corners = corners,
                arrowWidth = arrowWidth,
                arrowHeight = arrowHeight,
            )
        }
    )
}