package com.example.parcialtp3.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.model.TransactionIconType
import com.example.parcialtp3.domain.model.TransactionType
import com.example.parcialtp3.ui.theme.BlueButton
import com.example.parcialtp3.ui.theme.ParcialTP3Theme

/**
 * Item de transacción para mostrar en la lista
 */
@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono + Info
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                // Icono
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = BlueButton,
                    modifier = Modifier.size(48.dp)
                ) {
                    Image(
                        painter = painterResource(id = getIconForTransaction(transaction.iconType)),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.padding(12.dp)
                    )
                }

                // Título y Fecha
                Column {
                    Text(
                        text = transaction.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${transaction.time} - ${transaction.date}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            // Categoría
            Text(
                text = transaction.category,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // Monto
            Text(
                text = if (transaction.amount > 0) {
                    "$${String.format("%.2f", transaction.amount)}"
                } else {
                    "-$${String.format("%.2f", -transaction.amount)}"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (transaction.type == TransactionType.INCOME) Color.Black else Color(0xFF5A8DFF)
            )
        }
    }
}

/**
 * Obtiene el icono correspondiente al tipo de transacción desde drawable
 */
@DrawableRes
private fun getIconForTransaction(iconType: TransactionIconType): Int {
    return when (iconType) {
        TransactionIconType.SALARY -> R.drawable.home_salary
        TransactionIconType.GROCERIES -> R.drawable.cat_groceries
        TransactionIconType.RENT -> R.drawable.cat_rent
        TransactionIconType.TRANSFER -> R.drawable.cat_savings
        TransactionIconType.ENTERTAINMENT -> R.drawable.cat_entretenimiento
        TransactionIconType.BILLS -> R.drawable.cat_medicine
        TransactionIconType.SHOPPING -> R.drawable.cat_gifts
        TransactionIconType.FOOD -> R.drawable.cat_food
        TransactionIconType.TRANSPORT -> R.drawable.cat_transport
        TransactionIconType.OTHER -> R.drawable.cat_more
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItemPreview() {
    ParcialTP3Theme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TransactionItem(
                transaction = Transaction(
                    id = 1,
                    title = "Salary",
                    category = "Monthly",
                    amount = 4000.0,
                    date = "April 30",
                    time = "18:27",
                    type = TransactionType.INCOME,
                    iconType = TransactionIconType.SALARY
                )
            )
            TransactionItem(
                transaction = Transaction(
                    id = 2,
                    title = "Groceries",
                    category = "Pantry",
                    amount = -100.00,
                    date = "April 24",
                    time = "17:00",
                    type = TransactionType.EXPENSE,
                    iconType = TransactionIconType.GROCERIES
                )
            )
        }
    }
}
