package com.example.parcialtp3.presentation.components

import android.view.RoundedCorner
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.NavigationItem
import com.example.parcialtp3.ui.theme.FinGreenCard
import com.example.parcialtp3.ui.theme.FinGreenLight
import com.example.parcialtp3.ui.theme.FinLogoDark
import com.example.parcialtp3.ui.theme.ParcialTP3Theme

/**
 * Barra de navegación inferior con estado
 * Componente compartido que sabe qué pantalla está activa
 */
@Composable
fun BottomNavBar(
    selectedItem: NavigationItem,
    onItemSelected: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            ),
        color = FinGreenLight,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(
                iconRes = R.drawable.navbar_home,
                isSelected = selectedItem == NavigationItem.HOME,
                onClick = { onItemSelected(NavigationItem.HOME) }
            )
            NavBarItem(
                iconRes = R.drawable.navbar_analysis,
                isSelected = selectedItem == NavigationItem.STATS,
                onClick = { onItemSelected(NavigationItem.STATS) }
            )
            NavBarItem(
                iconRes = R.drawable.navbar_transactions,
                isSelected = selectedItem == NavigationItem.TRANSFER,
                onClick = { onItemSelected(NavigationItem.TRANSFER) }
            )
            NavBarItem(
                iconRes = R.drawable.navbar_category,
                isSelected = selectedItem == NavigationItem.WALLET,
                onClick = { onItemSelected(NavigationItem.WALLET) }
            )
            NavBarItem(
                iconRes = R.drawable.navbar_profile,
                isSelected = selectedItem == NavigationItem.PROFILE,
                onClick = { onItemSelected(NavigationItem.PROFILE) }
            )
        }
    }
}

/**
 * Item individual de la barra de navegación
 */
@Composable
private fun NavBarItem(
    @DrawableRes iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(48.dp)
    ) {
        if (isSelected) {
            // Icono activo con fondo verde
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = FinGreenCard,
                modifier = Modifier.size(48.dp)
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(FinLogoDark),
                    modifier = Modifier.padding(12.dp)
                )
            }
        } else {
            // Icono inactivo sin fondo
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                colorFilter = ColorFilter.tint(FinLogoDark),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    ParcialTP3Theme {
        BottomNavBar(
            selectedItem = NavigationItem.HOME,
            onItemSelected = {}
        )
    }
}
