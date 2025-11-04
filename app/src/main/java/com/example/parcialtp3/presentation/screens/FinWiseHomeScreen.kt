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
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.GoalsCard
import com.example.parcialtp3.presentation.components.NotificationButton
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
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Configurar color de status bar
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MainGreen.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    Scaffold(
        containerColor = BackgroundGreenWhiteAndLetters,
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
                .background(MainGreen)
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
                            .background(BackgroundGreenWhiteAndLetters)
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
                            .background(BackgroundGreenWhiteAndLetters)
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                    )
                }

                // Espacio final
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(BackgroundGreenWhiteAndLetters)
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
                    color = Color.Black
                )
                Text(
                    text = "Good Morning",
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.8f)
                )
            }

            NotificationButton(onNotificationClick)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Balance y Gastos
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 3.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.income),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(LettersAndIcons),
                        modifier = Modifier.padding(end = 4.dp)
                            .size(17.dp)
                    )
                    Text(
                        text = "Total Balance",
                        fontSize = 15.sp,
                        color = LettersAndIcons,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${String.format("%.2f", totalBalance)}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (totalBalance >= 0f) Color.White else BlueButton
                )
            }

            VerticalDivider(
                thickness = 1.dp,
                color = BackgroundGreenWhiteAndLetters,
                modifier = Modifier.height(60.dp)
            )


            Column(horizontalAlignment = Alignment.Start) {

                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.expense),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(LettersAndIcons),
                        modifier = Modifier.padding(end = 4.dp)
                            .size(17.dp)
                    )
                    Text(
                        text = "Total Expense",
                        fontSize = 15.sp,
                        color = LettersAndIcons,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "-$${String.format("%.2f", -totalExpense)}",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (totalExpense >= 0f) Color.White else BlueButton
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Barra de Progreso
        Column (
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .background(
                        color = LettersAndIcons,
                        shape = RoundedCornerShape(32.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "30%",
                    color = LightGreen,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .background(
                            color = LightGreen,
                            shape = RoundedCornerShape(28.dp)
                        )
                        .padding(start = 155.dp , end = 10.dp , top = 1.dp, bottom = 1.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "$20,000.00",
                        color = LettersAndIcons,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                    )
                }


            }

            Spacer(modifier = Modifier.height(8.dp))

            // Texto inferior
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = 7.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(LettersAndIcons),
                    modifier = Modifier.padding(horizontal = 10.dp)
                        .size(15.dp)
                )
                Text(
                    text = "$expensePercentage% Of Your Expenses, Looks Good.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = LettersAndIcons
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
