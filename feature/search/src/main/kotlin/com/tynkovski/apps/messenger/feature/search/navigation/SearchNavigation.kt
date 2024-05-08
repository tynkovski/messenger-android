package com.tynkovski.apps.messenger.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.search.SearchRoute

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch() = navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchScreen(
    navigatePopBack: () -> Unit,
    navigateToUser: (Long) -> Unit
) {
    composable(
        route = SEARCH_ROUTE,
        arguments = listOf(),
    ) {
        SearchRoute(
            navigateToUser = navigateToUser,
            navigatePopBack = navigatePopBack
        )
    }
}
