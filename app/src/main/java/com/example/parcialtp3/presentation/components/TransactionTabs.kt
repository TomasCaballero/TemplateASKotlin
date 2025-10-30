package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.presentation.viewmodels.TimeTab
import com.example.parcialtp3.ui.theme.FinGreenCard
import com.example.parcialtp3.ui.theme.ParcialTP3Theme

/**
 * Tabs para filtrar transacciones por periodo de tiempo
 */
@Composable
fun TransactionTabs(
    selectedTab: TimeTab,
    onTabSelected: (TimeTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50.dp),
        color = Color(0xFFF5F5F5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TabButton(
                text = "Daily",
                isSelected = selectedTab == TimeTab.DAILY,
                onClick = { onTabSelected(TimeTab.DAILY) },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                text = "Weekly",
                isSelected = selectedTab == TimeTab.WEEKLY,
                onClick = { onTabSelected(TimeTab.WEEKLY) },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                text = "Monthly",
                isSelected = selectedTab == TimeTab.MONTHLY,
                onClick = { onTabSelected(TimeTab.MONTHLY) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Botón individual de tab
 */
@Composable
private fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp),
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) FinGreenCard else Color.Transparent,
            contentColor = if (isSelected) Color.White else Color.Gray
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionTabsPreview() {
    ParcialTP3Theme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransactionTabs(
                selectedTab = TimeTab.MONTHLY,
                onTabSelected = {}
            )
            TransactionTabs(
                selectedTab = TimeTab.WEEKLY,
                onTabSelected = {}
            )
        }
    }
}
