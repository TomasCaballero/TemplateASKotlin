package com.example.parcialtp3.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.presentation.components.BottomNavBar

data class Category(
    val name: String,
    val iconRes: Int
)

@Composable
fun CategoriesScreen(
    totalBalance: Double = 7783.00,
    totalExpense: Double = 1187.40,
    expensePercentage: Int = 30,
    maxBudget: Double = 20000.00,
    onBackClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onNavigationItemSelected: (NavigationItem) -> Unit = {},
    onCategoryClick: (Category) -> Unit = {}
) {
    var selectedCategory by remember { mutableStateOf<Int?>(null) }

    val categories = listOf(
        Category("Food", R.drawable.cat_food),
        Category("Transport", R.drawable.cat_transport),
        Category("Medicine", R.drawable.cat_medicine),
        Category("Groceries", R.drawable.cat_groceries),
        Category("Rent", R.drawable.cat_rent),
        Category("Gift", R.drawable.cat_gifts),
        Category("Saving", R.drawable.cat_savings),
        Category("Entertainment", R.drawable.cat_entretenimiento),
        Category("More", R.drawable.cat_more)
    )

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
                // Header verde
                item {
                    CategoriesHeader(
                        totalBalance = totalBalance,
                        totalExpense = totalExpense,
                        expensePercentage = expensePercentage,
                        maxBudget = maxBudget,
                        onBackClick = onBackClick,
                        onNotificationClick = onNotificationClick
                    )
                }

                // Inicio de tarjeta blanca con categorías
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                            .background(LightGreen)
                            .padding(horizontal = 24.dp)
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))

                        // Grid de categorías 3x3
                        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            // Fila 1
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                CategoryItem(
                                    category = categories[0],
                                    isSelected = selectedCategory == 0,
                                    onClick = {
                                        selectedCategory = 0
                                        onCategoryClick(categories[0])
                                    }
                                )
                                CategoryItem(
                                    category = categories[1],
                                    isSelected = selectedCategory == 1,
                                    onClick = {
                                        selectedCategory = 1
                                        onCategoryClick(categories[1])
                                    }
                                )
                                CategoryItem(
                                    category = categories[2],
                                    isSelected = selectedCategory == 2,
                                    onClick = {
                                        selectedCategory = 2
                                        onCategoryClick(categories[2])
                                    }
                                )
                            }

                            // Fila 2
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                CategoryItem(
                                    category = categories[3],
                                    isSelected = selectedCategory == 3,
                                    onClick = {
                                        selectedCategory = 3
                                        onCategoryClick(categories[3])
                                    }
                                )
                                CategoryItem(
                                    category = categories[4],
                                    isSelected = selectedCategory == 4,
                                    onClick = {
                                        selectedCategory = 4
                                        onCategoryClick(categories[4])
                                    }
                                )
                                CategoryItem(
                                    category = categories[5],
                                    isSelected = selectedCategory == 5,
                                    onClick = {
                                        selectedCategory = 5
                                        onCategoryClick(categories[5])
                                    }
                                )
                            }

                            // Fila 3
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                CategoryItem(
                                    category = categories[6],
                                    isSelected = selectedCategory == 6,
                                    onClick = {
                                        selectedCategory = 6
                                        onCategoryClick(categories[6])
                                    }
                                )
                                CategoryItem(
                                    category = categories[7],
                                    isSelected = selectedCategory == 7,
                                    onClick = {
                                        selectedCategory = 7
                                        onCategoryClick(categories[7])
                                    }
                                )
                                CategoryItem(
                                    category = categories[8],
                                    isSelected = selectedCategory == 8,
                                    onClick = {
                                        selectedCategory = 8
                                        onCategoryClick(categories[8])
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun CategoriesHeader(
    totalBalance: Double,
    totalExpense: Double,
    expensePercentage: Int,
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
                    painter = painterResource(id = R.drawable.bring_back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = "Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = onNotificationClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home_campana),
                    contentDescription = "Notifications",
                    tint = MainGreen,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

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
                        painter = painterResource(id = R.drawable.expense),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Total Balance",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Text(
                    text = "$${String.format("%.2f", totalBalance)}",
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
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Total Expense",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Text(
                    text = "-$${String.format("%.2f", totalExpense)}",
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
                    text = "$expensePercentage%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = "$${String.format("%.2f", maxBudget)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { expensePercentage / 100f },
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
                text = "$expensePercentage% Of Your Expenses, Looks Good.",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.9f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Food usa azul oscuro (blue-600), el resto azul claro (blue-300)
    val backgroundColor = when (category.name) {
        "Food" -> Color(0xFF2563EB)  // blue-600
        else -> Color(0xFF93C5FD)     // blue-300
    }

    Column(
        modifier = Modifier
            .width(90.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = PoppinsFontFamily,
            color = LettersAndIcons,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CategoriesScreenPreview() {
    ParcialTP3Theme {
        CategoriesScreen()
    }
}
