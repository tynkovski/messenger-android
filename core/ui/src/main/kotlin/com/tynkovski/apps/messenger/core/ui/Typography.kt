package com.tynkovski.apps.messenger.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val TextStyle.semiBold @Composable get() = this.copy(fontWeight = FontWeight.SemiBold)
val TextStyle.bold @Composable get() = this.copy(fontWeight = FontWeight.Bold)
val TextStyle.black @Composable get() = this.copy(fontWeight = FontWeight.Black)
val TextStyle.medium @Composable get() = this.copy(fontWeight = FontWeight.Medium)
val TextStyle.light @Composable get() = this.copy(fontWeight = FontWeight.Light)
val TextStyle.thin @Composable get() = this.copy(fontWeight = FontWeight.Thin)
val TextStyle.normal @Composable get() = this.copy(fontWeight = FontWeight.Normal)
