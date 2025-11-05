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
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.model.TransactionIconType
import com.example.parcialtp3.domain.model.TransactionType
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.ui.theme.*
import java.util.Locale

/**
 * Pantalla de Transaction
 * Convertida desde transactions.html siguiendo el diseño de FinWise
 */
@Composable
fun TransactionScreen(
    totalBalance: Double = 7783.00,
    totalExpense: Double = -1187.40,
    maxBudget: Double = 20000.00,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    transactionsByMonth: Map<String, List<Transaction>> = getSampleTransactionsByMonth()
) {
    val usagePercentage = ((kotlin.math.abs(totalExpense) / maxBudget) * 100).toInt()
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    TransactionHeader(
                        totalBalance = totalBalance,
                        totalExpense = totalExpense,
                        usagePercentage = usagePercentage,
                        maxBudget = maxBudget,
                        onBackClick = onBackClick,
                        onNotificationClick = onNotificationClick
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                            .background(LightGreen)
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                transactionsByMonth.forEach { (month, transactions) ->
                    item {
                        // Month Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(LightGreen)
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

                    items(transactions) { transaction ->
                        TransactionItemScreen(
                            transaction = transaction,
                            modifier = Modifier
                                .background(LightGreen)
                                .padding(horizontal = 24.dp)
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(LightGreen)
                    )
                }
            }
        }
    }
}

@Composable
private fun TransactionHeader(
    totalBalance: Double,
    totalExpense: Double,
    usagePercentage: Int,
    maxBudget: Double,
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

        // Balance Summary - Lado a lado
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Total Balance
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_income),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Total Balance",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Text(
                    text = "$${String.format(Locale.US, "%.2f", totalBalance)}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Vertical Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(48.dp)
                    .background(Color.White.copy(alpha = 0.3f))
            )

            // Total Expense
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_expense),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Total Expense",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Text(
                    text = "-$${String.format(Locale.US, "%.2f", kotlin.math.abs(totalExpense))}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6B6B),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Progress Bar
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$usagePercentage%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "$${String.format(Locale.US, "%.2f", maxBudget)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { usagePercentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(50)),
                color = Color(0xFF2F2F2F),
                trackColor = Color.White.copy(alpha = 0.3f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Check Message
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$usagePercentage% Of Your Expenses, Looks Good.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TransactionItemScreen(transaction: Transaction, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono circular azul
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(BlueButton),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = getIconForTransaction(transaction.iconType)),
                contentDescription = transaction.title,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Información de la transacción
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F1F1F)
            )
            Text(
                text = "${transaction.time} - ${transaction.date}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        // Categoría
        Text(
            text = transaction.category,
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )

        // Divisor vertical
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .width(1.dp)
                .height(24.dp)
                .background(Color(0xFFD1D1D1))
        )

        // Monto
        Text(
            text = if (transaction.amount >= 0) {
                "$${String.format(Locale.US, "%.2f", transaction.amount)}"
            } else {
                "-$${String.format(Locale.US, "%.2f", kotlin.math.abs(transaction.amount))}"
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (transaction.amount >= 0) Color(0xFF1F1F1F) else Color(0xFFFF6B6B)
        )
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
