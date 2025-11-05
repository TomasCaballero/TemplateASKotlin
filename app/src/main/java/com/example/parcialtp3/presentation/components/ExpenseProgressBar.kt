package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.ui.theme.*

/**
 * Progress bar component showing expense percentage and limit
 */
@Composable
fun ExpenseProgressBar(
    expensePercentage: Int,
    expenseLimit: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .background(
                    color = LettersAndIcons,
                    shape = RoundedCornerShape(32.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$expensePercentage%",
                color = LightGreen,
                fontSize = 13.sp,
                modifier = Modifier.padding(start = 20.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .background(
                        color = LightGreen,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .padding(start = 155.dp, end = 10.dp, top = 1.dp, bottom = 1.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "$${String.format("%,.2f", expenseLimit)}",
                    color = LettersAndIcons,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseProgressBarPreview() {
    ParcialTP3Theme {
        ExpenseProgressBar(
            expensePercentage = 30,
            expenseLimit = 20000.00
        )
    }
}
