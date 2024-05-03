package com.tynkovski.apps.messenger.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.contacts.ContactsRoute

const val CONTACTS_ROUTE = "contacts_route"

fun NavController.navigateToContacts(navOptions: NavOptions) = navigate(CONTACTS_ROUTE, navOptions)

fun NavGraphBuilder.contactsScreen(

) {
    composable(
        route = CONTACTS_ROUTE,
        arguments = listOf(),
    ) {
        ContactsRoute()
    }
}
