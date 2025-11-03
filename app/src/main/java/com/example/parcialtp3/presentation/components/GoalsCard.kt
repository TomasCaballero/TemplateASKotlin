package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.FinGreenCard
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
            containerColor = FinGreenCard
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
                // Icono Coche con borde
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier
                        .size(64.dp)
                        .border(
                            width = 2.dp,
                            color = Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home_car),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Column {
                    Text(
                        text = "Savings",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Text(
                        text = "On Goals",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Separador
            Divider(
                modifier = Modifier
                    .height(64.dp)
                    .width(1.dp),
                color = Color.White.copy(alpha = 0.3f)
            )

            // Lado Derecho: Resumen Semanal
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Revenue Last Week
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home_income),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "Revenue Last Week",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "$${String.format("%.2f", revenueLastWeek)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                // Food Last Week
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home_food),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(20.dp)
                    )
                    Column {
                        Text(
                            text = "Food Last Week",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            text = "-$${String.format("%.2f", -foodLastWeek)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
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
