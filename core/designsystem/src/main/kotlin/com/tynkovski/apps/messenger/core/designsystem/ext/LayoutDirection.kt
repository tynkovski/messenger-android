package com.tynkovski.apps.messenger.core.designsystem.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

val isLtr @Composable get() = LocalLayoutDirection.current == LayoutDirection.Ltr
val isRtl @Composable get() = LocalLayoutDirection.current == LayoutDirection.Rtl