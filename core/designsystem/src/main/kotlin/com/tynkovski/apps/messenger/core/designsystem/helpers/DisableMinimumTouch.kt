package com.tynkovski.apps.messenger.core.designsystem.helpers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisableMinimumTouch(
    enabled: Boolean = true,
    block: @Composable () -> Unit,
) = CompositionLocalProvider(
    LocalMinimumInteractiveComponentEnforcement provides !enabled,
    content = block
)