package com.example.findapic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.findapic.ui.favorites.FavoriteImagesScreen
import com.example.findapic.ui.search.SearchImagesScreen

@Composable
fun BottomNavigationGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Search.route,
    ) {
        composable(route = Screens.Search.route) {
            SearchImagesScreen()
        }
        composable(route = Screens.Favorites.route) {
            FavoriteImagesScreen()
        }
    }
}