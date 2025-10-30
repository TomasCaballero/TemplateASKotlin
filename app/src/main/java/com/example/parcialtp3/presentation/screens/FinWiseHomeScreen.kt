package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.GoalsCard
import com.example.parcialtp3.presentation.components.TransactionItem
import com.example.parcialtp3.presentation.components.TransactionTabs
import com.example.parcialtp3.presentation.viewmodels.HomeViewModel
import com.example.parcialtp3.ui.theme.*

/**
 * Pantalla principal de FinWise (Home)
 * Muestra balance, gastos, transacciones y navegación inferior
 */
@Composable
fun FinWiseHomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onNavigationItemSelected: (NavigationItem) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
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
                .background(FinGreen)
        ) {
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
                        expenseLimit = uiState.expenseLimit
                    )
                }

                // Inicio de sección blanca con bordes superiores redondeados
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(FinGreenLight)
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        // Goals Card
                        GoalsCard(
                            revenueLastWeek = uiState.revenueLastWeek,
                            foodLastWeek = uiState.foodLastWeek,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Transaction Tabs
                        TransactionTabs(
                            selectedTab = uiState.selectedTab,
                            onTabSelected = { viewModel.selectTab(it) },
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))
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
                            .background(FinGreenLight)
                            .padding(horizontal = 24.dp, vertical = 6.dp)
                    )
                }

                // Espacio final
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(FinGreenLight)
                    )
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
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Saludo y Notificación
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hi, Welcome Back",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Good Morning",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            // Botón de notificación
            Box {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home_campana),
                        contentDescription = "Notifications",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Badge de notificación (punto rojo)
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-4).dp, y = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Balance y Gastos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TOTAL BALANCE",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format("%.2f", totalBalance)}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(1.dp),
                color = Color.White.copy(alpha = 0.3f)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "TOTAL EXPENSE",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "-$${String.format("%.2f", -totalExpense)}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Barra de Progreso
        Column {
            // Etiquetas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$expensePercentage%",
                    fontSize = 12.sp,
                    color = Color.White
                )
                Text(
                    text = "$${String.format("%.2f", expenseLimit)}",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Barra
            LinearProgressIndicator(
                progress = { expensePercentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                color = Color.DarkGray,
                trackColor = Color.White.copy(alpha = 0.3f),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Texto inferior
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "$expensePercentage% Of Your Expenses, Looks Good.",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FinWiseHomeScreenPreview() {
    ParcialTP3Theme {
        FinWiseHomeScreen()
    }
}
