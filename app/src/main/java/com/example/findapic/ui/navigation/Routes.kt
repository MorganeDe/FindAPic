package com.example.findapic.ui.navigation

sealed class Screens(val route: String) {
    data object Search : Screens(SEARCH_SCREEN_ROUTE)
    data object Favorites : Screens(FAVORITES_SCREEN_ROUTE)
}

private const val SEARCH_SCREEN_ROUTE = "search_route"
private const val FAVORITES_SCREEN_ROUTE = "favorites_route"