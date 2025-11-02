package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*
import androidx.compose.ui.text.font.FontStyle
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
        Category("Entretainment", R.drawable.cat_entretenimiento),
        Category("More", R.drawable.cat_more)
    )

    Scaffold(
        containerColor = FinGreenLight,
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
                .background(FinGreenLight)
                .padding(paddingValues)
        ) {
            // 1. HEADER VERDE CON INFO
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(FinGreen)
                    .zIndex(1f)
            ) {
                Column {
                    // Top Navigation Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .statusBarsPadding(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Back button
                        IconButton(onClick = onBackClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.bring_back),
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        // Title
                        Text(
                            text = "Categories",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = PoppinsFontFamily,
                            color = Color.Black
                        )

                        // Notification button
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
                                tint = FinGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // INFO DIRECTAMENTE EN EL VERDE
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(top = 16.dp)
                    ) {
                        // Total Balance y Total Expense CON DIVISOR
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Total Balance - CENTRADO
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.expense),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Total Balance",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = PoppinsFontFamily,
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "$${String.format("%.2f", totalBalance)}",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = PoppinsFontFamily,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // DIVISOR BLANCO VERTICAL
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(50.dp)
                                    .background(Color.White)
                            )

                            // Total Expense - CENTRADO
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.income),
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Total Expense",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Normal,
                                        fontFamily = PoppinsFontFamily,
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "-$${String.format("%.2f", totalExpense)}",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = PoppinsFontFamily,
                                    color = Color(0xFF4C8AFF),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Progress Bar con labels DENTRO
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Barra de fondo negro
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.Black)
                            )

                            // Barra de progreso blanca
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(expensePercentage / 100f)
                                    .height(32.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                            )

                            // Labels DENTRO de la barra
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(32.dp)
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // 30% (en negro sobre blanco)
                                Text(
                                    text = "$expensePercentage%",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = PoppinsFontFamily,
                                    color = Color.Black
                                )

                                // $20,000.00 (en blanco sobre negro)
                                Text(
                                    text = "$20,000.00",
                                    fontSize = 14.sp,
                                    fontStyle = FontStyle.Italic,
                                    fontFamily = PoppinsFontFamily,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Texto CENTRADO
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.check),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$expensePercentage% Of Your Expenses, Looks Good.",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Normal,
                                fontFamily = PoppinsFontFamily,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // 2. CARD BLANCO SUPERPUESTO
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 280.dp)
                    .zIndex(2f)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    color = FinGreenLight,
                    shadowElevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(top = 32.dp)
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 24.dp)
                    ) {
                        // Título Categories
                        Text(
                            text = "Categories",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = PoppinsFontFamily,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Grid de categorías
                        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            // FILA 1
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

                            // FILA 2
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

                            // FILA 3
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

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(90.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    if (isSelected) Color(0xFF4C8AFF)
                    else Color(0xFFB3D9FF)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = category.iconRes),
                contentDescription = category.name,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PoppinsFontFamily,
            color = Color.Black
        )
    }
}