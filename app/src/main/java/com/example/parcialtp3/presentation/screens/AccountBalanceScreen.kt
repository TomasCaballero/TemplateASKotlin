package com.example.parcialtp3.presentation.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.ui.theme.*
import java.util.Locale

@Composable
fun AccountBalanceScreen(
    totalBalance: Double = 7783.00,
    totalExpense: Double = -1187.40,
    income: Double = 4000.00,
    expense: Double = -1187.40,
    maxBudget: Double = 20000.00,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    transactions: List<Transaction> = getSampleTransactions()
) {
    val usagePercentage = ((kotlin.math.abs(totalExpense) / maxBudget) * 100).toInt()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = FinGreen.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    Scaffold(
        containerColor = FinWhite,
        bottomBar = {
            BottomNavBar(
                selectedItem = NavigationItem.STATS,
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    AccountBalanceHeader(
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
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(FinWhite)
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        IncomeExpenseCards(income = income, expense = expense)
                        Spacer(modifier = Modifier.height(16.dp))
                        CheckMessage(usagePercentage = usagePercentage)
                        Spacer(modifier = Modifier.height(24.dp))
                        TransactionsHeader(onSeeAllClick = onSeeAllClick)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                items(transactions) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        modifier = Modifier
                            .background(FinWhite)
                            .padding(horizontal = 24.dp)
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(FinWhite)
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountBalanceHeader(
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
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Image(
                    painter = painterResource(id = R.drawable.profile_logout), // TODO: Replace with back arrow
                    contentDescription = "Back",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = "Account Balance",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            IconButton(
                onClick = onNotificationClick,
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
        }

        // Balance Cards
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BalanceCard(
                label = "Total Balance",
                amount = totalBalance,
                modifier = Modifier.weight(1f)
            )
            BalanceCard(
                label = "Total Expense",
                amount = totalExpense,
                amountColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.weight(1f)
            )
        }

        // Progress Bar
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$usagePercentage%",
                    fontSize = 12.sp,
                    color = Color.White
                )
                Text(
                    text = "${String.format(Locale.US, "%.2f", maxBudget)}",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { usagePercentage / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                color = Color.DarkGray,
                trackColor = Color.White.copy(alpha = 0.3f),
            )
        }
    }
}

@Composable
private fun BalanceCard(
    label: String,
    amount: Double,
    modifier: Modifier = Modifier,
    amountColor: Color = Color.White
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.2f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "${String.format(Locale.US, "%.2f", amount)}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }
    }
}

@Composable
private fun IncomeExpenseCards(income: Double, expense: Double) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InfoCard(
            label = "Income",
            amount = income,
            icon = R.drawable.home_income,
            iconTint = FinGreen,
            modifier = Modifier.weight(1f)
        )
        InfoCard(
            label = "Expense",
            amount = expense,
            icon = R.drawable.home_expense,
            iconTint = FinIconBlue,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun InfoCard(
    label: String,
    amount: Double,
    icon: Int,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FinWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, FinGreenPale)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    colorFilter = ColorFilter.tint(iconTint),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FinLogoDark
                )
            }
            Text(
                text = "${String.format(Locale.US, "%.2f", amount)}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = FinLogoDark
            )
        }
    }
}

@Composable
private fun CheckMessage(usagePercentage: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = FinGreen,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$usagePercentage% Of Your Expenses, Looks Good.",
            fontSize = 12.sp,
            color = FinLogoDark.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TransactionsHeader(onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Transactions",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = FinLogoDark
        )
        Text(
            text = "See all",
            fontSize = 14.sp,
            color = FinLogoDark.copy(alpha = 0.6f),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}

@Composable
fun TransactionItem(transaction: Transaction, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(FinGreenPale),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = transaction.iconRes),
                    contentDescription = transaction.title,
                    colorFilter = ColorFilter.tint(FinGreen),
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = FinLogoDark
                )
                Text(
                    text = transaction.dateTime,
                    fontSize = 12.sp,
                    color = FinLogoDark.copy(alpha = 0.6f)
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (transaction.amount >= 0) {
                        "+${String.format(Locale.US, "%.2f", transaction.amount)}"
                    } else {
                        "-${String.format(Locale.US, "%.2f", kotlin.math.abs(transaction.amount))}"
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (transaction.amount >= 0) FinGreen else MaterialTheme.colorScheme.error
                )
                Text(
                    text = transaction.category,
                    fontSize = 12.sp,
                    color = FinLogoDark.copy(alpha = 0.6f)
                )
            }
        }
        Divider(color = FinGreenPale, thickness = 1.dp)
    }
}

data class Transaction(
    val title: String,
    val dateTime: String,
    val amount: Double,
    val category: String,
    val iconRes: Int
)

fun getSampleTransactions(): List<Transaction> {
    return listOf(
        Transaction("Salary", "18:27 - April 30", 4000.00, "Monthly", R.drawable.home_salary),
        Transaction("Groceries", "17:00 - April 24", -100.00, "Pantry", R.drawable.cat_groceries),
        Transaction("Rent", "8:30 - April 15", -674.40, "Rent", R.drawable.cat_rent),
        Transaction("Transport", "9:30 - April 08", -4.13, "Fuel", R.drawable.cat_transport)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountBalanceScreenPreview() {
    ParcialTP3Theme {
        AccountBalanceScreen()
    }
}
