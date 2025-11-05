package com.example.parcialtp3.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import com.example.parcialtp3.presentation.components.BalanceCard
import com.example.parcialtp3.presentation.components.BottomNavBar
import com.example.parcialtp3.presentation.components.ExpenseProgressBar
import com.example.parcialtp3.presentation.components.NotificationButton

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
                            .background(BackgroundGreenWhiteAndLetters)
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

                        Spacer(modifier = Modifier.height(24.dp + paddingValues.calculateBottomPadding()))
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
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
            NotificationButton(onNotificationClick = onNotificationClick)
        }

        // Balance y Gastos
        BalanceCard(
            totalBalance = totalBalance,
            totalExpense = totalExpense
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Barra de Progreso
        ExpenseProgressBar(
            expensePercentage = expensePercentage,
            expenseLimit = maxBudget
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
