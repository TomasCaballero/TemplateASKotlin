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
import com.example.parcialtp3.presentation.screens.OnboardingScreen1
import com.example.parcialtp3.presentation.screens.OnboardingScreen2
import com.example.parcialtp3.presentation.screens.LoginScreen
import com.example.parcialtp3.presentation.screens.FinWiseHomeScreen

/**
 * Navigation routes sealed class
 * Defines all possible navigation destinations
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Launch : Screen("launch")
    data object OnboardingScreen1 : Screen("onboarding1")
    data object OnboardingScreen2 : Screen("onboarding2")
    data object Login : Screen("login")

    data object FinWiseHome : Screen("finwise_home")
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
                    navController.navigate(Screen.Login.route)
                },
                onSignUpClick = {
                    navController.navigate(Screen.OnboardingScreen1.route)
                },
                onForgotPasswordClick = {
                    // TODO: Navegar a pantalla de recuperación de contraseña
                }
            )
        }

        // Onboarding Screen 1
        composable(route = Screen.OnboardingScreen1.route) {
            OnboardingScreen1(
                onNextClick = {
                    navController.navigate(Screen.OnboardingScreen2.route)
                }
            )
        }

        // Onboarding Screen 2
        composable(route = Screen.OnboardingScreen2.route) {
            OnboardingScreen2(
                onNextClick = {
                    navController.navigate(Screen.FinWiseHome.route)
                }
            )
        }

        // Login Screen
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    // TODO: Implementar lógica de autenticación
                    navController.navigate(Screen.FinWiseHome.route) {
                        // Limpia el back stack para que no pueda volver a login
                        popUpTo(Screen.Launch.route) {
                            inclusive = false
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Screen.OnboardingScreen1.route)
                },
                onForgotPasswordClick = {
                    // TODO: Navegar a pantalla de recuperación de contraseña
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // FinWise Home Screen
        composable(route = Screen.FinWiseHome.route) {
            FinWiseHomeScreen(
                onNavigationItemSelected = { navigationItem ->
                    // TODO: Implementar navegación entre pantallas del bottom nav
                }
            )
        }

        // Home Screen (Template original)
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
