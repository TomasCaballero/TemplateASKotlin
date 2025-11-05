package com.example.parcialtp3.presentation.screens

import android.app.Activity
import android.health.connect.datatypes.units.Percentage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.parcialtp3.presentation.components.BalanceCard
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.ExpenseProgressBar
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.presentation.components.TransactionItem
import com.example.parcialtp3.ui.theme.*
import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.model.TransactionIconType
import com.example.parcialtp3.domain.model.TransactionType
import java.util.Locale
import kotlin.math.abs

@Composable
fun AccountBalanceScreen(
    totalBalance: Double = 7783.00,
    totalExpense: Double = -1187.40,
    income: Double = 4000.0,
    expense: Double = -1187.40,
    maxBudget: Double = 20000.00,
    expensePercentage: Int = 30,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    transactions: List<Transaction> = getSampleTransactions()
) {
    val usagePercentage = ((abs(totalExpense) / maxBudget) * 100).toInt()

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
                selectedItem = NavigationItem.STATS,
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
                    AccountBalanceHeader(
                        totalBalance = totalBalance,
                        totalExpense = totalExpense,
                        usagePercentage = usagePercentage,
                        maxBudget = maxBudget,
                        income = income,
                        expense = expense,
                        onBackClick = onBackClick,
                        onNotificationClick = onNotificationClick,
                        expensePercentage = expensePercentage
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
                        TransactionsHeader(onSeeAllClick = onSeeAllClick)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                items(items = transactions, key = { it.id }) { transaction ->
                    TransactionItem(
                        transaction = transaction,
                        modifier = Modifier
                            .background(BackgroundGreenWhiteAndLetters)
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                    )
                }

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

@Composable
private fun AccountBalanceHeader(
    totalBalance: Double,
    totalExpense: Double,
    usagePercentage: Int,
    maxBudget: Double,
    income: Double,
    expense: Double,
    expensePercentage: Int,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Navigation Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = "Account Balance",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            NotificationButton(onNotificationClick = onNotificationClick)
        }

        // Balance Summary
        BalanceCard(
            totalBalance = totalBalance,
            totalExpense = totalExpense,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Progress Bar
        ExpenseProgressBar(
            expensePercentage = expensePercentage,
            expenseLimit = maxBudget
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Income/Expense Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoCardGreen(
                label = "Income",
                amount = income,
                icon = R.drawable.home_income,
                iconTint = MainGreen,
                modifier = Modifier.weight(1f),
                type = TransactionType.INCOME
            )
            InfoCardGreen(
                label = "Expense",
                amount = expense,
                icon = R.drawable.home_expense,
                iconTint = BlueButton,
                modifier = Modifier.weight(1f),
                type = TransactionType.EXPENSE
            )
        }
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
    }
}

@Composable
private fun InfoCardGreen(
    label: String,
    amount: Double,
    type: TransactionType,
    icon: Int,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundGreenWhiteAndLetters)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().
                    padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    colorFilter = ColorFilter.tint(iconTint),
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                    text = label,
                    fontSize = 14.sp,
                    color = LettersAndIcons,
                    fontWeight = FontWeight.Medium
                )
            Text(
                    text = if (amount >= 0) {
                        "$${String.format(Locale.US, "%.2f", amount)}"
                    } else {
                        "-$${String.format(Locale.US, "%.2f", abs(amount))}"
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if ( type.equals(TransactionType.INCOME)) LettersAndIcons else BlueButton
                )
            }
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
            color = LettersAndIcons
        )
        Text(
            text = "See all",
            fontSize = 14.sp,
            color = LettersAndIcons.copy(alpha = 0.6f),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}

fun getSampleTransactions(): List<Transaction> {
    return listOf(
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
            time = "9:30",
            date = "April 08",
            amount = -4.13,
            category = "Fuel",
            type = TransactionType.EXPENSE,
            iconType = TransactionIconType.TRANSPORT
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountBalanceScreenPreview() {
    ParcialTP3Theme {
        AccountBalanceScreen()
    }
}
