package com.example.parcialtp3.presentation.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BalanceCard
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.ExpenseProgressBar
import com.example.parcialtp3.presentation.components.GoalsCard
import com.example.parcialtp3.presentation.components.LoadingIndicator
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.presentation.components.TransactionItem
import com.example.parcialtp3.presentation.components.TransactionTabs
import com.example.parcialtp3.presentation.viewmodels.HomeViewModel
import com.example.parcialtp3.ui.theme.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parcialtp3.presentation.components.ErrorMessage

/**
 * Pantalla principal de FinWise (Home)
 * Muestra balance, gastos, transacciones y navegación inferior
 */
@Composable
fun FinWiseHomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Configurar color de status bar
    val view = LocalView.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MainGreen.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    Scaffold(
        containerColor = surfaceColor,
        bottomBar = {
            BottomNavBar(
                selectedItem = NavigationItem.HOME,
                onItemSelected = onNavigationItemSelected
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            // Show loading indicator
            if (uiState.isLoading) {
                LoadingIndicator()
            }

            // Show error message
            else if (uiState.error != null) {
                ErrorMessage(
                    message = uiState.error ?: "Unknown error",
                    onRetry = { viewModel.retryLoadTransactions() }
                )
            }

            // Show content
            else {
                // Contenido desplazable
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                // Header (sobre fondo verde)
                item {
                    HomeHeader(
                        totalBalance = uiState.totalBalance,
                        totalExpense = uiState.totalExpense,
                        expensePercentage = uiState.expensePercentage,
                        expenseLimit = uiState.expenseLimit,
                        onNotificationClick = onNotificationClick
                    )
                }

                // Inicio de sección blanca con bordes superiores redondeados
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        // Goals Card
                        GoalsCard(
                            revenueLastWeek = uiState.revenueLastWeek,
                            foodLastWeek = uiState.foodLastWeek,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        // Transaction Tabs
                        TransactionTabs(
                            selectedTab = uiState.selectedTab,
                            onTabSelected = { viewModel.selectTab(it) },
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )

                        Spacer(modifier = Modifier.height(17.dp))
                    }
                }

                // Lista de transacciones (continúa el fondo blanco)
                items(
                    items = uiState.transactions,
                    key = { it.id }
                ) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                    )
                }

                // Espacio final
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(MaterialTheme.colorScheme.surface)
                    )
                }
            }
            }
        }
    }
}

/**
 * Header de la pantalla con saludo, balance y barra de progreso
 */
@Composable
private fun HomeHeader(
    totalBalance: Double,
    totalExpense: Double,
    expensePercentage: Int,
    expenseLimit: Double,
    onNotificationClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Saludo y Notificación
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hi, Welcome Back",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Good Morning",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
            }

            NotificationButton(onNotificationClick)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Balance y Gastos
        BalanceCard(
            totalBalance = totalBalance,
            totalExpense = totalExpense
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Barra de Progreso
        ExpenseProgressBar(
            expensePercentage = expensePercentage,
            expenseLimit = expenseLimit
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FinWiseHomeScreenPreview() {
    ParcialTP3Theme {
        FinWiseHomeScreen()
    }
}
