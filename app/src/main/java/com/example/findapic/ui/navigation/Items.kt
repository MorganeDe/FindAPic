package com.example.findapic.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

fun getBottomNavigationItems() = listOf(
    BottomNavigationItem(
        label = SEARCH_NAVIGATION_ITEM_LABEL,
        icon = Icons.Default.Search,
        route = Screens.Search.route,
    ),
    BottomNavigationItem(
        label = FAVORITES_NAVIGATION_ITEM_LABEL,
        icon = Icons.Default.Favorite,
        route = Screens.Favorites.route,
    ),
)

private const val SEARCH_NAVIGATION_ITEM_LABEL = "Search"
private const val FAVORITES_NAVIGATION_ITEM_LABEL = "Favorites"