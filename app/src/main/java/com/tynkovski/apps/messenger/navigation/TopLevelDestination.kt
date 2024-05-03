package com.tynkovski.apps.messenger.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.tynkovski.apps.messenger.core.designsystem.icon.MessengerIcons
import com.tynkovski.apps.messenger.feature.chats.R as chatsR
import com.tynkovski.apps.messenger.feature.contacts.R as contactsR
import com.tynkovski.apps.messenger.feature.settings.R as settingsR

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    CONTACTS(
        selectedIcon = MessengerIcons.Contacts,
        unselectedIcon = MessengerIcons.Contacts,
        iconTextId = contactsR.string.feature_contacts_title_contacts,
        titleTextId = contactsR.string.feature_contacts_title_contacts,
    ),
    CHATS(
        selectedIcon = MessengerIcons.Message,
        unselectedIcon = MessengerIcons.Message,
        iconTextId = chatsR.string.feature_chats_title_chats,
        titleTextId = chatsR.string.feature_chats_title_chats,
    ),
    SETTINGS(
        selectedIcon = MessengerIcons.Settings,
        unselectedIcon = MessengerIcons.Settings,
        iconTextId = settingsR.string.feature_settings_title_settings,
        titleTextId = settingsR.string.feature_settings_title_settings,
    )
}
