package com.tynkovski.apps.messenger.core.designsystem.helpers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager

inline fun Modifier.ifTrue(
    value: Boolean,
    block: Modifier.() -> Modifier,
): Modifier = if (value) block.invoke(this) else this

inline fun Modifier.ifFalse(
    value: Boolean,
    block: Modifier.() -> Modifier,
): Modifier = if (value.not()) block.invoke(this) else this

@Composable
fun Modifier.clearingFocusClickable(
    onClick: () -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    focusManager: FocusManager = LocalFocusManager.current,
): Modifier {
    return this then clickable(
        indication = null,
        interactionSource = interactionSource,
        onClick = {
            focusManager.clearFocus()
            onClick()
        }
    )
}


inline fun Modifier.chose(
    value: Boolean,
    block: Modifier.() -> Modifier,
    another: Modifier.() -> Modifier
): Modifier = if (value) block.invoke(this) else another.invoke(this)
