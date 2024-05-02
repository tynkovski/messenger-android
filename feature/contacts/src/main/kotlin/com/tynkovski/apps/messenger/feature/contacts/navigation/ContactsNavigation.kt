package com.tynkovski.apps.messenger.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.contacts.ContactsRoute

const val _ROUTE = "_route"

fun NavController.navigateToContacts(navOptions: NavOptions) = navigate(_ROUTE, navOptions)

fun NavGraphBuilder.ContactsScreen(

) {
    composable(
        route = _ROUTE,
        arguments = listOf(),
    ) {
        ContactsRoute()
    }
}
