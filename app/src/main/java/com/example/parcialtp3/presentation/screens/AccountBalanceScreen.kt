package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.ui.theme.*
import java.util.Locale

/**
 * Pantalla de Account Balance
 * Convertida desde accountBalanceScreen.html con diseño de cards flotantes
 */
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
    // Calcular el porcentaje usado
    val usagePercentage = ((kotlin.math.abs(totalExpense) / maxBudget) * 100).toInt()

    Scaffold(
        containerColor = Color(0xFF0d9488), // Emerald 700
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
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // TOP SECTION - Green Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0d9488))
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 56.dp)
                ) {
                    // Navigation Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back button
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onBackClick() }
                        )

                        // Title
                        Text(
                            text = "Account Balance",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )

                        // Notification button
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable { onNotificationClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Total Balance and Total Expense Cards
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Total Balance Card
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                ) {
                                    Text(
                                        text = "$",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.75f),
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    Text(
                                        text = "Total Balance",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.75f)
                                    )
                                }
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", totalBalance)}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // Total Expense Card
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.2f)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                ) {
                                    Text(
                                        text = "⚡",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.75f),
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    Text(
                                        text = "Total Expense",
                                        fontSize = 12.sp,
                                        color = Color.White.copy(alpha = 0.75f)
                                    )
                                }
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", totalExpense)}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFfca5a5) // red-300
                                )
                            }
                        }
                    }

                    // Progress Bar
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF10b981)) // emerald-500
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(usagePercentage / 100f)
                                    .background(Color.White)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$usagePercentage%",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", maxBudget)}",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                // WHITE CARD SECTION - Overlapping the green section
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (-40).dp),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Income/Expense Cards
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Income Card
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFf3f4f6))
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.home_income),
                                            contentDescription = "Income",
                                            tint = Color(0xFF10b981), // emerald-500
                                            modifier = Modifier
                                                .size(24.dp)
                                                .padding(end = 8.dp)
                                        )
                                        Text(
                                            text = "Income",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF374151) // gray-700
                                        )
                                    }
                                    Text(
                                        text = "$${String.format(Locale.US, "%.2f", income)}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1f2937) // gray-800
                                    )
                                }
                            }

                            // Expense Card
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFf3f4f6))
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.home_expense),
                                            contentDescription = "Expense",
                                            tint = Color(0xFF3b82f6), // blue-500
                                            modifier = Modifier
                                                .size(24.dp)
                                                .padding(end = 8.dp)
                                        )
                                        Text(
                                            text = "Expense",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF374151) // gray-700
                                        )
                                    }
                                    Text(
                                        text = "$${String.format(Locale.US, "%.2f", expense)}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1f2937) // gray-800
                                    )
                                }
                            }
                        }

                        // Check Message
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "✓ ",
                                fontSize = 12.sp,
                                color = Color(0xFF10b981),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "$usagePercentage% Of Your Expenses, Looks Good.",
                                fontSize = 12.sp,
                                color = Color(0xFF4b5563), // gray-600
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Transactions Section
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Transactions",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1f2937) // gray-800
                            )
                            Text(
                                text = "See all",
                                fontSize = 14.sp,
                                color = Color(0xFF6b7280), // gray-500
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.clickable { onSeeAllClick() }
                            )
                        }

                        // Transaction List
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            items(transactions) { transaction ->
                                TransactionItem(transaction = transaction)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Container
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFdbeafe)), // blue-100
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = transaction.iconRes),
                contentDescription = transaction.title,
                tint = Color(0xFF3b82f6), // blue-600
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Transaction Details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = transaction.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1f2937) // gray-800
            )
            Text(
                text = transaction.dateTime,
                fontSize = 12.sp,
                color = Color(0xFF6b7280) // gray-500
            )
        }

        // Amount and Category
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = if (transaction.amount >= 0) {
                    "$${String.format(Locale.US, "%.2f", transaction.amount)}"
                } else {
                    "-$${String.format(Locale.US, "%.2f", kotlin.math.abs(transaction.amount))}"
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (transaction.amount >= 0) Color(0xFF10b981) else Color(0xFFdc2626) // emerald-600 or red-600
            )
            Text(
                text = transaction.category,
                fontSize = 12.sp,
                color = Color(0xFF6b7280) // gray-500
            )
        }
    }

    // Divider (except for last item)
    Divider(
        color = Color(0xFFf3f4f6), // gray-100
        thickness = 1.dp
    )
}

// Data class for transactions
data class Transaction(
    val title: String,
    val dateTime: String,
    val amount: Double,
    val category: String,
    val iconRes: Int
)

// Sample data
fun getSampleTransactions(): List<Transaction> {
    return listOf(
        Transaction(
            title = "Salary",
            dateTime = "18:27 - April 30",
            amount = 4000.00,
            category = "Monthly",
            iconRes = R.drawable.home_salary
        ),
        Transaction(
            title = "Groceries",
            dateTime = "17:00 - April 24",
            amount = -100.00,
            category = "Pantry",
            iconRes = R.drawable.cat_groceries
        ),
        Transaction(
            title = "Rent",
            dateTime = "8:30 - April 15",
            amount = -674.40,
            category = "Rent",
            iconRes = R.drawable.cat_rent
        ),
        Transaction(
            title = "Transport",
            dateTime = "9:30 - April 08",
            amount = -4.13,
            category = "Fuel",
            iconRes = R.drawable.cat_transport
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
