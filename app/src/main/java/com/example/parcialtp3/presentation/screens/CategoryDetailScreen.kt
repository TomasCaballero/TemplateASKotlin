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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parcialtp3.R
import com.example.parcialtp3.data.local.entities.ExpenseEntity
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.NotificationButton
import com.example.parcialtp3.presentation.viewmodels.ExpenseViewModel
import com.example.parcialtp3.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Locale

data class CategoryTransaction(
    val title: String,
    val dateTime: String,
    val amount: Double,
    val iconRes: Int
)

@Composable
fun CategoryDetailScreen(
    categoryName: String = "Food",
    totalBalance: Double = 7783.00,
    totalExpense: Double = -1187.40,
    maxBudget: Double = 20000.00,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onAddExpenseClick: () -> Unit = {},
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    // Load expenses for this category
    LaunchedEffect(categoryName) {
        viewModel.loadExpensesByCategory(categoryName)
    }

    val expenses by viewModel.expenses.collectAsState()
    val dateFormat = SimpleDateFormat("HH:mm - MMMM dd", Locale.getDefault())

    // Group expenses by month
    val transactionsByMonth = expenses.groupBy { expense ->
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = expense.date
        SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)
    }

    // Calculate total expense from database
    val calculatedTotalExpense = expenses.sumOf { it.amount }

    val usagePercentage = if (maxBudget > 0) {
        ((kotlin.math.abs(calculatedTotalExpense) / maxBudget) * 100).toInt()
    } else {
        0
    }

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
                selectedItem = NavigationItem.WALLET,
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
                    CategoryDetailHeader(
                        categoryName = categoryName,
                        totalBalance = totalBalance,
                        totalExpense = calculatedTotalExpense,
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
                            .background(BackgroundGreenWhiteAndLetters)
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                transactionsByMonth.forEach { (month, expensesList) ->
                    item {
                        // Month Header con Calendar Button
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
                                fontWeight = FontWeight.SemiBold,
                                color = DarkModeGreenBar
                            )
                            // Calendar Button
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF20C997))
                                    .clickable { /* TODO: Open calendar */ },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Calendar",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    items(expensesList) { expense ->
                        ExpenseTransactionItem(
                            expense = expense,
                            dateFormat = dateFormat,
                            modifier = Modifier
                                .background(BackgroundGreenWhiteAndLetters)
                                .padding(horizontal = 24.dp)
                        )
                    }
                }

                // Add Expense Button
                item {
                    Button(
                        onClick = onAddExpenseClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundGreenWhiteAndLetters)
                            .padding(horizontal = 24.dp)
                            .padding(top = 32.dp, bottom = 24.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF20C997)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Add Expenses",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
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
private fun CategoryDetailHeader(
    categoryName: String,
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
            IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
            Text(
                text = categoryName,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            NotificationButton(onNotificationClick = onNotificationClick)
        }

        // Balance Summary
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
                        painter = painterResource(id = R.drawable.expense),
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
                        painter = painterResource(id = R.drawable.income),
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
                    color = BlueButton,
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
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ExpenseTransactionItem(
    expense: ExpenseEntity,
    dateFormat: SimpleDateFormat,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BlueButton),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = getCategoryIcon(expense.categoryName)),
                    contentDescription = expense.title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Transaction info
            Column {
                Text(
                    text = expense.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkModeGreenBar
                )
                Text(
                    text = dateFormat.format(java.util.Date(expense.date)),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        // Amount
        Text(
            text = "-$${String.format(Locale.US, "%.2f", kotlin.math.abs(expense.amount))}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = BlueButton
        )
    }
}

@Composable
fun CategoryTransactionItem(
    transaction: CategoryTransaction,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon circle
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BlueButton),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = transaction.iconRes),
                    contentDescription = transaction.title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Transaction info
            Column {
                Text(
                    text = transaction.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkModeGreenBar
                )
                Text(
                    text = transaction.dateTime,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        // Amount
        Text(
            text = "-$${String.format(Locale.US, "%.2f", kotlin.math.abs(transaction.amount))}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = BlueButton
        )
    }
}

fun getCategoryIcon(categoryName: String): Int {
    return when (categoryName.lowercase()) {
        "food" -> R.drawable.cat_food
        "transport" -> R.drawable.cat_transport
        "medicine", "health" -> R.drawable.cat_medicine
        "groceries" -> R.drawable.cat_groceries
        "rent" -> R.drawable.cat_rent
        "entertainment" -> R.drawable.cat_entretenimiento
        "education" -> R.drawable.cat_savings
        "shopping" -> R.drawable.cat_gifts
        else -> R.drawable.cat_food // Default icon
    }
}

fun getSampleCategoryTransactions(): Map<String, List<CategoryTransaction>> {
    return mapOf(
        "April" to listOf(
            CategoryTransaction(
                title = "Dinner",
                dateTime = "18:27 - April 30",
                amount = -26.00,
                iconRes = R.drawable.cat_food
            ),
            CategoryTransaction(
                title = "Delivery Pizza",
                dateTime = "15:00 - April 24",
                amount = -18.35,
                iconRes = R.drawable.cat_food
            ),
            CategoryTransaction(
                title = "Lunch",
                dateTime = "12:30 - April 15",
                amount = -15.40,
                iconRes = R.drawable.cat_food
            ),
            CategoryTransaction(
                title = "Brunch",
                dateTime = "9:30 - April 08",
                amount = -12.13,
                iconRes = R.drawable.cat_food
            )
        ),
        "March" to listOf(
            CategoryTransaction(
                title = "Dinner",
                dateTime = "20:50 - March 31",
                amount = -27.20,
                iconRes = R.drawable.cat_food
            )
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoryDetailScreenPreview() {
    ParcialTP3Theme {
        CategoryDetailScreen()
    }
}
