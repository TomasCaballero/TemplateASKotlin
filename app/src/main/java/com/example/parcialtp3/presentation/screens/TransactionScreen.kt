package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.model.TransactionIconType
import com.example.parcialtp3.domain.model.TransactionType
import com.example.parcialtp3.presentation.components.BalanceCard
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.ExpenseProgressBar
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.presentation.components.TransactionItem
import com.example.parcialtp3.presentation.viewmodels.TransactionUiState
import com.example.parcialtp3.presentation.viewmodels.TransactionViewModel
import com.example.parcialtp3.ui.theme.*
import java.util.Locale

/**
 * Pantalla de Transaction
 * Convertida desde transactions.html siguiendo el diseño de FinWise
 */
@Composable
fun TransactionScreen(
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = BackgroundGreenWhiteAndLetters,
        bottomBar = {
            BottomNavBar(
                selectedItem = NavigationItem.TRANSFER,
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
            when (val state = uiState) {
                is TransactionUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is TransactionUiState.Success -> {
                    TransactionContent(
                        totalBalance = state.totalBalance,
                        totalExpense = state.totalExpense,
                        transactionsByMonth = state.transactionsByMonth,
                        onBackClick = onBackClick,
                        onNotificationClick = onNotificationClick
                    )
                }
                is TransactionUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.message,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.retry() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BlueButton
                                )
                            ) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionContent(
    totalBalance: Double,
    totalExpense: Double,
    transactionsByMonth: Map<String, List<Transaction>>,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            TransactionHeader(
                totalBalance = totalBalance,
                totalExpense = totalExpense,
                onBackClick = onBackClick,
                onNotificationClick = onNotificationClick,
                expenseLimit = 20000.00,
                expensePercentage = 30
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .background(BackgroundGreenWhiteAndLetters)
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        transactionsByMonth.forEach { (month, transactions) ->
            item {
                // Mes
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BackgroundGreenWhiteAndLetters)
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = month,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F1F1F)
                    )
                }
            }

            // Lista de transacciones (continúa el fondo blanco)
            items(
                items = transactions,
                key = { it.id }
            ) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    modifier = Modifier
                        .background(BackgroundGreenWhiteAndLetters)
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(BackgroundGreenWhiteAndLetters )
            )
        }
    }
}

@Composable
private fun TransactionHeader(
    totalBalance: Double,
    totalExpense: Double,
    expensePercentage: Int,
    expenseLimit: Double,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        // Navigation Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = "Transaction",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            NotificationButton(onNotificationClick = onNotificationClick)
        }

        // Tarjeta blanca de Total Balance
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundGreenWhiteAndLetters),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Balance",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "$${String.format(Locale.US, "%.2f", totalBalance)}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = LettersAndIcons,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        // Check Message
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.check),
                contentDescription = null,
                colorFilter = ColorFilter.tint(LettersAndIcons),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(15.dp)
            )
            Text(
                text = "$expensePercentage% Of Your Expenses, Looks Good.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = LettersAndIcons
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Helper function to get icon resource from TransactionIconType
private fun getIconForTransaction(iconType: TransactionIconType): Int {
    return when (iconType) {
        TransactionIconType.SALARY -> R.drawable.home_salary
        TransactionIconType.GROCERIES -> R.drawable.cat_groceries
        TransactionIconType.RENT -> R.drawable.cat_rent
        TransactionIconType.TRANSFER -> R.drawable.cat_savings
        TransactionIconType.ENTERTAINMENT -> R.drawable.cat_entretenimiento
        TransactionIconType.BILLS -> R.drawable.cat_medicine
        TransactionIconType.SHOPPING -> R.drawable.cat_gifts
        TransactionIconType.FOOD -> R.drawable.cat_food
        TransactionIconType.TRANSPORT -> R.drawable.cat_transport
        TransactionIconType.OTHER -> R.drawable.cat_more
    }
}

// Data class para transacciones agrupadas por mes
data class TransactionGroup(
    val month: String,
    val transactions: List<Transaction>
)

// Sample data agrupada por mes
fun getSampleTransactionsByMonth(): Map<String, List<Transaction>> {
    return mapOf(
        "April" to listOf(
            Transaction(
                id = 1,
                title = "Salary",
                time = "18:27",
                date = "April 30",
                amount = 4000.00,
                category = "Monthly",
                type = TransactionType.INCOME,
                iconType = TransactionIconType.SALARY
            ),
            Transaction(
                id = 2,
                title = "Groceries",
                time = "17:00",
                date = "April 24",
                amount = -100.00,
                category = "Pantry",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.GROCERIES
            ),
            Transaction(
                id = 3,
                title = "Rent",
                time = "8:30",
                date = "April 15",
                amount = -674.40,
                category = "Rent",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.RENT
            ),
            Transaction(
                id = 4,
                title = "Transport",
                time = "7:30",
                date = "April 08",
                amount = -4.13,
                category = "Fuel",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.TRANSPORT
            )
        ),
        "March" to listOf(
            Transaction(
                id = 5,
                title = "Food",
                time = "19:30",
                date = "March 31",
                amount = -70.40,
                category = "Dinner",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.FOOD
            )
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionScreenPreview() {
    ParcialTP3Theme {
        TransactionScreen()
    }
}
