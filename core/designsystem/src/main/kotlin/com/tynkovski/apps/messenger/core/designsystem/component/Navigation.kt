package com.tynkovski.apps.messenger.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.core.designsystem.theme.MessengerTheme

@Composable
fun RowScope.MessengerNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MessengerNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MessengerNavigationDefaults.navigationContentColor(),
            selectedTextColor = MessengerNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = MessengerNavigationDefaults.navigationContentColor(),
            indicatorColor = MessengerNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun MessengerNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = MessengerNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

object MessengerNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

@ThemePreviews
@Composable
fun NiaNavigationBarPreview() {
    val items = listOf("Contacts", "Chats", "Settings")

    val icons = listOf(
        MessengerIcons.Contacts,
        MessengerIcons.Message,
        MessengerIcons.Settings,
    )

    MessengerTheme {
        MessengerNavigationBar {
            items.forEachIndexed { index, item ->
                MessengerNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}