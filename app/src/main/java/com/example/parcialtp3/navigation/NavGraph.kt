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
import com.example.parcialtp3.presentation.screens.LaunchScreen
import com.example.parcialtp3.presentation.screens.SplashScreen

/**
 * Navigation routes sealed class
 * Defines all possible navigation destinations
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Launch : Screen("launch")
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
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen - se muestra 2 segundos antes de ir a Launch
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigateToLaunch = {
                    navController.navigate(Screen.Launch.route) {
                        // Elimina Splash del back stack para que no se pueda volver
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Launch Screen
        composable(route = Screen.Launch.route) {
            LaunchScreen(
                onLoginClick = {
                    navController.navigate(Screen.Home.route)
                },
                onSignUpClick = {
                    // TODO: Navegar a pantalla de registro cuando esté implementada
                    navController.navigate(Screen.Home.route)
                },
                onForgotPasswordClick = {
                    // TODO: Navegar a pantalla de recuperación de contraseña
                }
            )
        }

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
