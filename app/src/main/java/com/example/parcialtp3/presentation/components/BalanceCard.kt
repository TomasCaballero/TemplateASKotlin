package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import com.example.parcialtp3.ui.theme.*

/**
 * Card component that displays Total Balance and Total Expense side by side
 */
@Composable
fun BalanceCard(
    totalBalance: Double,
    totalExpense: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 3.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.income),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(LettersAndIcons),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(17.dp)
                )
                Text(
                    text = "Total Balance",
                    fontSize = 15.sp,
                    color = LettersAndIcons,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$${String.format("%.2f", totalBalance)}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = if (totalBalance >= 0f) Color.White else BlueButton
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            color = BackgroundGreenWhiteAndLetters,
            modifier = Modifier.height(60.dp)
        )

        Column(horizontalAlignment = Alignment.Start) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.expense),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(LettersAndIcons),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(17.dp)
                )
                Text(
                    text = "Total Expense",
                    fontSize = 15.sp,
                    color = LettersAndIcons,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "-$${String.format("%.2f", -totalExpense)}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = if (totalExpense >= 0f) Color.White else BlueButton
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BalanceCardPreview() {
    ParcialTP3Theme {
        BalanceCard(
            totalBalance = 15000.00,
            totalExpense = -5000.00
        )
    }
}
