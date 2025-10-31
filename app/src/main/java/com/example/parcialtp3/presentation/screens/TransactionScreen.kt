package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
 * Pantalla de Transaction
 * Convertida desde transactionScreen.html con diseño de card flotante
 */
@Composable
fun TransactionScreen(
    totalBalance: Double = 7783.00,
    income: Double = 4120.00,
    expense: Double = -1187.40,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onCalendarClick: () -> Unit = {},
    transactionsByMonth: Map<String, List<Transaction>> = getSampleTransactionsByMonth()
) {
    Scaffold(
        containerColor = Color(0xFF0d9488), // Emerald 700
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
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // TOP SECTION - Green Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0d9488))
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp, bottom = 80.dp)
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
                            text = "Transaction",
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
                }

                // WHITE CARD SECTION
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(y = (-60).dp),
                    shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Space for floating card
                        Spacer(modifier = Modifier.height(80.dp))

                        // Income/Expense Cards
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
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
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.home_income),
                                            contentDescription = "Income",
                                            tint = Color(0xFF10b981), // emerald-600
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 4.dp)
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
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.home_expense),
                                            contentDescription = "Expense",
                                            tint = Color(0xFF3b82f6), // blue-500
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(end = 4.dp)
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

                        // Transactions List by Month
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            transactionsByMonth.forEach { (month, transactions) ->
                                item {
                                    // Month Header
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp, bottom = 12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = month,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1f2937) // gray-800
                                        )
                                        Icon(
                                            imageVector = Icons.Default.DateRange,
                                            contentDescription = "Calendar",
                                            tint = Color(0xFF10b981), // emerald-600
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable { onCalendarClick() }
                                        )
                                    }
                                }

                                items(transactions) { transaction ->
                                    TransactionItem(transaction = transaction)
                                }
                            }
                        }
                    }
                }
            }

            // FLOATING TOTAL BALANCE CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.TopCenter)
                    .offset(y = 60.dp)
                    .zIndex(10f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
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
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF6b7280) // gray-500
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$${String.format(Locale.US, "%.2f", totalBalance)}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1f2937) // gray-800
                    )
                }
            }
        }
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
                dateTime = "7:30 - April 08",
                amount = -4.13,
                category = "Fuel",
                iconRes = R.drawable.cat_transport
            )
        ),
        "March" to listOf(
            Transaction(
                title = "Food",
                dateTime = "19:30 - March 31",
                amount = -70.40,
                category = "Dinner",
                iconRes = R.drawable.cat_food
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
