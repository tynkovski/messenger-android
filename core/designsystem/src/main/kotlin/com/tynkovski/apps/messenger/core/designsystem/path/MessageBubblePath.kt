package com.tynkovski.apps.messenger.core.designsystem.path

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

object MessageBubblePath {
    fun drawBubbleRightPath(
        size: Size,
        density: Density,
        showTail: Boolean,
        corners: Dp,
        arrowWidth: Dp,
        arrowHeight: Dp,
        offset: Float = 0f,
    ): Path {
        return Path().apply {
            reset()

            val corners = corners.value * 2f * density.density
            val arrowWidth = arrowWidth.value * density.density
            val arrowHeight = arrowHeight.value * density.density

            moveTo(offset + corners, 0f)

            lineTo(offset + size.width - corners, 0f)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + size.width - corners - arrowWidth, 0f),
                    size = Size(corners, corners)
                ), startAngleDegrees = 270f, sweepAngleDegrees = 90f, forceMoveTo = false
            )

            lineTo(offset + size.width - arrowWidth, size.height - arrowHeight)

            val tailWidth = arrowWidth * 2.7f
            val tailHeight = arrowHeight / 4.5f

            if (showTail) {
                quadraticBezierTo(
                    x1 = offset + size.width - arrowWidth,
                    y1 = size.height,
                    x2 = offset + size.width,
                    y2 = size.height
                )

                quadraticBezierTo(
                    x1 = offset + size.width + arrowWidth - corners,
                    y1 = size.height + tailHeight / 2f,
                    x2 = offset + size.width - corners - tailWidth / 2f,
                    y2 = size.height - corners - tailHeight,
                )
            }

            arcTo(
                rect = Rect(
                    offset = Offset(
                        offset + size.width - corners - arrowWidth, size.height - corners
                    ), size = Size(corners, corners)
                ), startAngleDegrees = 0f, sweepAngleDegrees = 90f, forceMoveTo = false
            )

            lineTo(offset + corners, size.height)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + 0f, size.height - corners),
                    size = Size(corners, corners)
                ), startAngleDegrees = 90f, sweepAngleDegrees = 90f, forceMoveTo = false
            )

            lineTo(offset + 0f, size.height - corners)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + 0f, 0f), size = Size(corners, corners)
                ), startAngleDegrees = 180f, sweepAngleDegrees = 90f, forceMoveTo = false
            )

            close()
        }
    }

    fun drawBubbleLeftPath(
        size: Size,
        density: Density,
        showTail: Boolean,
        corners: Dp,
        arrowWidth: Dp,
        arrowHeight: Dp,
        offset: Float = 0f,
    ): Path {
        return Path().apply {
            reset()

            val corners = corners.value * 2f * density.density
            val arrowWidth = arrowWidth.value * density.density
            val arrowHeight = arrowHeight.value * density.density

            moveTo(offset + corners + arrowWidth, 0f)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + 0f + arrowWidth, 0f), size = Size(corners, corners)
                ), startAngleDegrees = 270f, sweepAngleDegrees = -90f, forceMoveTo = false
            )

            lineTo(offset + 0f + arrowWidth, size.height - arrowHeight)

            val tailWidth = arrowWidth * 2.7f
            val tailHeight = arrowHeight / 4.5f

            if (showTail) {
                quadraticBezierTo(
                    x1 = offset + 0f + arrowWidth,
                    y1 = size.height,
                    x2 = offset + 0f,
                    y2 = size.height
                )

                quadraticBezierTo(
                    x1 = offset + 0f - arrowWidth + corners,
                    y1 = size.height + tailHeight / 2f,
                    x2 = offset + 0f + corners + tailWidth / 2f,
                    y2 = size.height - corners - tailHeight,
                )
            }

            arcTo(
                rect = Rect(
                    offset = Offset(offset + 0f + arrowWidth, size.height - corners),
                    size = Size(corners, corners)
                ), startAngleDegrees = 180f, sweepAngleDegrees = -90f, forceMoveTo = false
            )

            lineTo(offset + size.width - corners, size.height)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + size.width - corners, size.height - corners),
                    size = Size(corners, corners)
                ), startAngleDegrees = 90f, sweepAngleDegrees = -90f, forceMoveTo = false
            )

            lineTo(offset + size.width, corners)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + size.width - corners, 0f),
                    size = Size(corners, corners)
                ), startAngleDegrees = 0f, sweepAngleDegrees = -90f, forceMoveTo = false
            )

            close()
        }
    }

    fun drawBubbleRightPathVariant(
        size: Size,
        density: Density,
        showTail: Boolean,
        corners: Dp,
        arrowWidth: Dp,
        arrowHeight: Dp,
        offset: Float = 0f,
    ): Path {
        return Path().apply {
            reset()

            val corners = corners.value * 2f * density.density
            val arrowWidth = arrowWidth.value * density.density
            val arrowHeight = arrowHeight.value * density.density

            moveTo(offset + corners, 0f)

            lineTo(offset + size.width - corners, 0f)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + size.width - corners - arrowWidth, 0f),
                    size = Size(corners, corners)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            lineTo(offset + size.width - arrowWidth, size.height - arrowHeight)

            if (showTail) {
                quadraticBezierTo(
                    x1 = offset + size.width - arrowWidth,
                    y1 = size.height,
                    x2 = offset + size.width,
                    y2 = size.height
                )
            } else {
                arcTo(
                    rect = Rect(
                        offset = Offset(
                            offset + size.width - corners - arrowWidth, size.height - corners
                        ), size = Size(corners, corners)
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }

            lineTo(offset + corners, size.height)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + 0f, size.height - corners),
                    size = Size(corners, corners)
                ), startAngleDegrees = 90f, sweepAngleDegrees = 90f, forceMoveTo = false
            )

            lineTo(offset + 0f, size.height - corners)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + 0f, 0f), size = Size(corners, corners)
                ), startAngleDegrees = 180f, sweepAngleDegrees = 90f, forceMoveTo = false
            )

            close()
        }
    }

    fun drawBubbleLeftPathVariant(
        size: Size,
        density: Density,
        showTail: Boolean,
        corners: Dp,
        arrowWidth: Dp,
        arrowHeight: Dp,
        offset: Float = 0f,
    ): Path {
        return Path().apply {
            reset()

            val corners = corners.value * 2f * density.density
            val arrowWidth = arrowWidth.value * density.density
            val arrowHeight = arrowHeight.value * density.density

            moveTo(offset + corners + arrowWidth, 0f)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + 0f + arrowWidth, 0f), size = Size(corners, corners)
                ), startAngleDegrees = 270f, sweepAngleDegrees = -90f, forceMoveTo = false
            )

            lineTo(offset + 0f + arrowWidth, size.height - arrowHeight)

            if (showTail) {
                quadraticBezierTo(
                    x1 = offset + 0f + arrowWidth,
                    y1 = size.height,
                    x2 = offset + 0f,
                    y2 = size.height
                )
            } else {
                arcTo(
                    rect = Rect(
                        offset = Offset(offset + 0f + arrowWidth, size.height - corners),
                        size = Size(corners, corners)
                    ), startAngleDegrees = 180f, sweepAngleDegrees = -90f, forceMoveTo = false
                )
            }

            lineTo(offset + size.width - corners, size.height)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + size.width - corners, size.height - corners),
                    size = Size(corners, corners)
                ), startAngleDegrees = 90f, sweepAngleDegrees = -90f, forceMoveTo = false
            )

            lineTo(offset + size.width, corners)

            arcTo(
                rect = Rect(
                    offset = Offset(offset + size.width - corners, 0f),
                    size = Size(corners, corners)
                ), startAngleDegrees = 0f, sweepAngleDegrees = -90f, forceMoveTo = false
            )

            close()
        }
    }
}