package com.tynkovski.apps.messenger.feature.contacts.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.contacts.ContactsRoute

const val CONTACTS_ROUTE = "contacts_route"

fun NavController.navigateToContacts(navOptions: NavOptions) = navigate(CONTACTS_ROUTE, navOptions)

fun NavGraphBuilder.contactsScreen(
    onUserClick: (Long) -> Unit
) {
    composable(
        route = CONTACTS_ROUTE,
        arguments = listOf(),
    ) {
        ContactsRoute(
            modifier = Modifier.fillMaxSize(),
            onUserClick = onUserClick,
        )
    }
}
