package com.tynkovski.apps.messenger.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tynkovski.apps.messenger.core.designsystem.component.TransparentIconButton
import com.tynkovski.apps.messenger.core.designsystem.helpers.clearingFocusClickable
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons

@Composable
fun SignBase(
    modifier: Modifier,
    showIcon: Boolean,
    backClick: () -> Unit,
    content: @Composable ColumnScope. () -> Unit,
) = Scaffold(
    modifier = modifier,
    contentWindowInsets = WindowInsets.navigationBars
        .union(WindowInsets.statusBars)
        .union(WindowInsets.displayCutout)
        .union(WindowInsets.ime)
) { paddingValues ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState()) // todo resolve exception
                .clearingFocusClickable(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content.invoke(this@Column)
        }

        if (showIcon) {
            TransparentIconButton(
                imageVector = MessengerIcons.ArrowBack,
                onClick = backClick,
            )
        }
    }
}