package com.tynkovski.apps.messenger.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.tynkovski.apps.messenger.feature.search.SearchRoute

const val _ROUTE = "_route"

fun NavController.navigateToSearch(navOptions: NavOptions) = navigate(_ROUTE, navOptions)

fun NavGraphBuilder.SearchScreen(

) {
    composable(
        route = _ROUTE,
        arguments = listOf(),
    ) {
        SearchRoute()
    }
}
