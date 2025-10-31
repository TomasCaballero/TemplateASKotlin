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
import com.example.parcialtp3.presentation.screens.ForgotPasswordScreen1
import com.example.parcialtp3.presentation.screens.ForgotPasswordScreen2
import com.example.parcialtp3.presentation.screens.ForgotPasswordScreen3
import com.example.parcialtp3.presentation.screens.ForgotPasswordScreen4
import com.example.parcialtp3.presentation.screens.ProfileScreen
import com.example.parcialtp3.presentation.screens.AccountBalanceScreen

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
    data object ForgotPassword1 : Screen("forgot_password1")
    data object ForgotPassword2 : Screen("forgot_password2")
    data object ForgotPassword3 : Screen("forgot_password3")
    data object ForgotPassword4 : Screen("forgot_password4")

    data object FinWiseHome : Screen("finwise_home")
    data object Profile : Screen("profile")
    data object AccountBalance : Screen("account_balance")
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
                    navController.navigate(Screen.ForgotPassword1.route)
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
                onLoginSuccess = {
                    // Navegar a FinWiseHome cuando el login sea exitoso
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
                    navController.navigate(Screen.ForgotPassword1.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Forgot Password Screen 1 - Enter Email
        composable(route = Screen.ForgotPassword1.route) {
            ForgotPasswordScreen1(
                onNextStepClick = { email ->
                    navController.navigate(Screen.ForgotPassword2.route)
                },
                onSignUpClick = {
                    navController.navigate(Screen.OnboardingScreen1.route)
                }
            )
        }

        // Forgot Password Screen 2 - Security Pin
        composable(route = Screen.ForgotPassword2.route) {
            ForgotPasswordScreen2(
                onAcceptClick = { pin ->
                    navController.navigate(Screen.ForgotPassword3.route)
                },
                onSendAgainClick = {
                    // TODO: Implementar reenvío de PIN
                },
                onSignUpClick = {
                    navController.navigate(Screen.OnboardingScreen1.route)
                }
            )
        }

        // Forgot Password Screen 3 - New Password
        composable(route = Screen.ForgotPassword3.route) {
            ForgotPasswordScreen3(
                onChangePasswordClick = { newPassword, confirmPassword ->
                    // TODO: Validar que las contraseñas coincidan
                    navController.navigate(Screen.ForgotPassword4.route)
                }
            )
        }

        // Forgot Password Screen 4 - Success
        composable(route = Screen.ForgotPassword4.route) {
            ForgotPasswordScreen4(
                onNavigateNext = {
                    navController.navigate(Screen.FinWiseHome.route)
                }
            )
        }

        // FinWise Home Screen
        composable(route = Screen.FinWiseHome.route) {
            FinWiseHomeScreen(
                onNavigationItemSelected = { navigationItem ->
                    when (navigationItem) {
                        com.example.parcialtp3.domain.model.NavigationItem.PROFILE -> {
                            navController.navigate(Screen.Profile.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.STATS -> {
                            navController.navigate(Screen.AccountBalance.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.HOME -> {
                            // Ya estamos en Home, no hacer nada
                        }
                        // TODO: Implementar navegación para otros items del bottom nav
                        else -> {
                            // Por ahora, los otros items no navegan
                        }
                    }
                }
            )
        }

        // Profile Screen
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigationItemSelected = { navigationItem ->
                    when (navigationItem) {
                        com.example.parcialtp3.domain.model.NavigationItem.HOME -> {
                            navController.popBackStack()
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.STATS -> {
                            navController.navigate(Screen.AccountBalance.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.PROFILE -> {
                            // Ya estamos en Profile, no hacer nada
                        }
                        // TODO: Implementar navegación para otros items del bottom nav
                        else -> {
                            // Por ahora, los otros items no navegan
                        }
                    }
                }
            )
        }

        // Account Balance Screen
        composable(route = Screen.AccountBalance.route) {
            AccountBalanceScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigationItemSelected = { navigationItem ->
                    when (navigationItem) {
                        com.example.parcialtp3.domain.model.NavigationItem.HOME -> {
                            navController.navigate(Screen.FinWiseHome.route) {
                                popUpTo(Screen.FinWiseHome.route) { inclusive = false }
                            }
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.PROFILE -> {
                            navController.navigate(Screen.Profile.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.STATS -> {
                            // Ya estamos en AccountBalance, no hacer nada
                        }
                        // TODO: Implementar navegación para otros items del bottom nav
                        else -> {
                            // Por ahora, los otros items no navegan
                        }
                    }
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
