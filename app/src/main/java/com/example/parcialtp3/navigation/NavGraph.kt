package com.example.parcialtp3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.parcialtp3.presentation.screens.*

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Launch : Screen("launch")
    data object OnboardingScreen1 : Screen("onboarding1")
    data object OnboardingScreen2 : Screen("onboarding2")
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object ForgotPassword1 : Screen("forgot_password1")
    data object ForgotPassword2 : Screen("forgot_password2")
    data object ForgotPassword3 : Screen("forgot_password3")
    data object ForgotPassword4 : Screen("forgot_password4")

    data object FinWiseHome : Screen("finwise_home")
    data object Notification : Screen("notification")
    data object Profile : Screen("profile")
    data object EditProfile : Screen("edit_profile")
    data object Security : Screen("security")
    data object AccountBalance : Screen("account_balance")
    data object Transaction : Screen("transaction")
    data object Categories : Screen("categories")  // ← AGREGADO
    data object ChangePin : Screen("change_pin")
    data object Successfully : Screen("successfully/{message}") {
        fun createRoute(message: String) = "successfully/$message"
    }

    data object Home : Screen("home")
    data object Create : Screen("create")
    data object Detail : Screen("detail/{itemId}") {
        fun createRoute(itemId: Long) = "detail/$itemId"
    }
}

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

        composable(route = Screen.Launch.route) {
            LaunchScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onSignUpClick = {
                    navController.navigate(Screen.Signup.route)
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPassword1.route)
                }
            )
        }

        composable(route = Screen.OnboardingScreen1.route) {
            OnboardingScreen1(
                onNextClick = {
                    navController.navigate(Screen.OnboardingScreen2.route)
                }
            )
        }

        composable(route = Screen.OnboardingScreen2.route) {
            OnboardingScreen2(
                onNextClick = {
                    navController.navigate(Screen.FinWiseHome.route)
                }
            )
        }

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
                    navController.navigate(Screen.Signup.route)
                },
                onForgotPasswordClick = {
                    navController.navigate(Screen.ForgotPassword1.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Signup.route) {
            SignupScreen(
                onSignUpSuccess = {
                    navController.navigate(Screen.FinWiseHome.route) {
                        popUpTo(Screen.Launch.route) {
                            inclusive = false
                        }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

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

        composable(route = Screen.ForgotPassword3.route) {
            ForgotPasswordScreen3(
                onChangePasswordClick = { newPassword, confirmPassword ->
                    // TODO: Validar que las contraseñas coincidan
                    navController.navigate(Screen.ForgotPassword4.route)
                }
            )
        }

        composable(route = Screen.ForgotPassword4.route) {
            ForgotPasswordScreen4(
                onNavigateNext = {
                    navController.navigate(Screen.FinWiseHome.route)
                }
            )
        }

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
                        com.example.parcialtp3.domain.model.NavigationItem.TRANSFER -> {
                            navController.navigate(Screen.Transaction.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.WALLET -> {
                            navController.navigate(Screen.Categories.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.HOME -> {
                            // Ya estamos en Home, no hacer nada
                        }
                        // TODO: Implementar navegación para otros items del bottom nav
                        else -> {
                            // Por ahora, los otros items no navegan
                        }
                    }
                },
                onNotificationClick = {
                    navController.navigate(Screen.Notification.route)
                }
            )
        }

        composable(route = Screen.Notification.route) {
            NotificationScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

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
                        com.example.parcialtp3.domain.model.NavigationItem.TRANSFER -> {
                            navController.navigate(Screen.Transaction.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.WALLET -> {
                            navController.navigate(Screen.Categories.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.PROFILE -> {
                            // Ya estamos en Profile, no hacer nada
                        }
                        // TODO: Implementar navegación para otros items del bottom nav
                        else -> {
                            // Por ahora, los otros items no navegan
                        }
                    }
                },
                onNotificationClick = {
                    navController.navigate(Screen.Notification.route)
                },
                onEditProfileClick = {
                    navController.navigate(Screen.EditProfile.route)
                },
                onSecurityClick = {
                    navController.navigate(Screen.Security.route)
                },
                onSettingClick = {
                },
                onHelpClick = {
                },
                onLogoutClick = {
                    navController.navigate(Screen.Launch.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.EditProfile.route) {
            EditProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = {
                    navController.navigate(Screen.Notification.route)
                },
                onUpdateProfile = { username, phone, email, pushNotif, darkTheme ->
                    navController.popBackStack()
                }
            )
        }

        composable(route = Screen.Security.route) {
            SecurityScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = {
                    navController.navigate(Screen.Notification.route)
                },
                onChangePinClick = {
                    navController.navigate(Screen.ChangePin.route)
                },
                onFingerprintClick = {
                },
                onTermsClick = {
                }
            )
        }

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
                        com.example.parcialtp3.domain.model.NavigationItem.TRANSFER -> {
                            navController.navigate(Screen.Transaction.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.WALLET -> {
                            navController.navigate(Screen.Categories.route)
                        }
                        else -> {
                            // Por ahora, los otros items no navegan
                        }
                    }
                }
            )
        }

        // Transaction Screen
        composable(route = Screen.Transaction.route) {
            TransactionScreen(
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
                            navController.navigate(Screen.AccountBalance.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.TRANSFER -> {
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.WALLET -> {
                            navController.navigate(Screen.Categories.route)
                        }
                        else -> {
                        }
                    }
                }
            )
        }

        //  CATEGORIES SCREEN - AQUÍ ESTÁ EL CAMBIO PRINCIPAL
        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = {
                    navController.navigate(Screen.Notification.route)
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
                            navController.navigate(Screen.AccountBalance.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.TRANSFER -> {
                            navController.navigate(Screen.Transaction.route)
                        }
                        com.example.parcialtp3.domain.model.NavigationItem.WALLET -> {
                            // Ya estamos en Categories, no hacer nada
                        }
                        else -> {
                            // Por ahora, los otros items no navegan
                        }
                    }
                },
                onCategoryClick = { category ->
                    // TODO: Navegar a detalles de categoría si es necesario
                }
            )
        }

        composable(route = Screen.ChangePin.route) {
            ChangePinScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNotificationClick = {
                    navController.navigate(Screen.Notification.route)
                },
                onSuccess = { message ->
                    navController.navigate(Screen.Successfully.createRoute(message))
                }
            )
        }

        composable(
            route = "successfully/{message}",
            arguments = listOf(navArgument("message") { type = NavType.StringType })
        ) { backStackEntry ->
            val message = backStackEntry.arguments?.getString("message") ?: "Success"
            SuccessfullyScreen(
                message = message,
                onNavigateBack = {
                    navController.popBackStack(Screen.Security.route, false)
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
