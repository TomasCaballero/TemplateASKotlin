package com.example.parcialtp3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.parcialtp3.presentation.screens.DetailScreen
import com.example.parcialtp3.presentation.screens.HomeScreen
import com.example.parcialtp3.presentation.screens.CreateScreen

/**
 * Navigation routes sealed class
 * Defines all possible navigation destinations
 */
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Create : Screen("create")
    data object Detail : Screen("detail/{itemId}") {
        fun createRoute(itemId: Long) = "detail/$itemId"
    }
}

/**
 * Main navigation graph
 * Defines navigation structure and routes
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Home Screen
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToCreate = {
                    navController.navigate(Screen.Create.route)
                },
                onNavigateToDetail = { itemId ->
                    navController.navigate(Screen.Detail.createRoute(itemId))
                }
            )
        }

        // Create Screen
        composable(route = Screen.Create.route) {
            CreateScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // Detail Screen with argument
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getLong("itemId") ?: 0L
            DetailScreen(
                itemId = itemId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
