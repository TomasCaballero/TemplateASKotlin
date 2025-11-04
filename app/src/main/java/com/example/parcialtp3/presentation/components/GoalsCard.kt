package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.BackgroundGreenWhiteAndLetters
import com.example.parcialtp3.ui.theme.BlueButton
import com.example.parcialtp3.ui.theme.LettersAndIcons
import com.example.parcialtp3.ui.theme.LightGreen
import com.example.parcialtp3.ui.theme.MainGreen
import com.example.parcialtp3.ui.theme.ParcialTP3Theme

/**
 * Tarjeta de ahorros con estadísticas semanales
 */
@Composable
fun GoalsCard(
    revenueLastWeek: Double,
    foodLastWeek: Double,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MainGreen
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lado Izquierdo: Savings
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.width(120.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Surface(
                        shape = CircleShape,
                        color = MainGreen,
                        modifier = Modifier
                            .size(64.dp)
                            .border(
                                width = 2.dp,
                                brush = Brush.horizontalGradient(
                                    0.0f to LightGreen,
                                    0.5f to LightGreen,
                                    0.5f to BlueButton,
                                    1.0f to BlueButton
                                ),
                                shape = CircleShape
                            )
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.home_car),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(LettersAndIcons),
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Savings",
                            fontSize = 12.sp,
                            color = LettersAndIcons
                        )
                        Text(
                            text = "On Goals",
                            fontSize = 12.sp,
                            color = LettersAndIcons
                        )
                    }
                }

                VerticalDivider(
                    thickness = 2.dp,
                    color = BackgroundGreenWhiteAndLetters,
                    modifier = Modifier.height(120.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Revenue Last Week
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.home_salary),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(LettersAndIcons),
                            modifier = Modifier.size(35.dp)
                        )
                        Column {
                            Text(
                                text = "Revenue Last Week",
                                fontSize = 12.sp,
                                color = LettersAndIcons
                            )
                            Text(
                                text = "$${String.format("%.2f", revenueLastWeek)}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (revenueLastWeek >= 0f) LettersAndIcons else BlueButton
                            )
                        }
                    }

                    HorizontalDivider(
                        thickness = 2.dp,
                        color = BackgroundGreenWhiteAndLetters,
                        modifier = Modifier.width(200.dp)
                    )

                    // Food Last Week
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.home_food),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(LettersAndIcons),
                            modifier = Modifier.size(35.dp)
                        )
                        Column {
                            Text(
                                text = "Food Last Week",
                                fontSize = 12.sp,
                                color = LettersAndIcons
                            )
                            Text(
                                text = "-$${String.format("%.2f", -foodLastWeek)}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (foodLastWeek >= 0f) LettersAndIcons else BlueButton
                            )
                        }
                    }
                }

            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GoalsCardPreview() {
    ParcialTP3Theme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            GoalsCard(
                revenueLastWeek = 4000.0,
                foodLastWeek = -100.00
            )
        }
    }
}
