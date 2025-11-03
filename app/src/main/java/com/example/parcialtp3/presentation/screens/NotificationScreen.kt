package com.example.parcialtp3.presentation.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.Notification
import com.example.parcialtp3.domain.model.NotificationGroup
import com.example.parcialtp3.domain.model.NotificationType
import com.example.parcialtp3.presentation.components.NotificationGroupComponent
import com.example.parcialtp3.ui.theme.*

/**
 * Pantalla de Notificaciones de FinWise
 * Muestra las notificaciones agrupadas por período de tiempo
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(onBackClick: () -> Unit = {}) {
    // Configurar color de status bar
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = MainGreen.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    val notificationGroups = getExampleNotifications()

    Scaffold(
        containerColor = LightGreen,
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
                item {
                    NotificationHeader(
                        onBackClick = onBackClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp, bottom = 40.dp)
                    )
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .background(LightGreen)
                            .padding(24.dp)
                    ) {
                        notificationGroups.forEach { group ->
                            NotificationGroupComponent(
                                group = group,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Header de la pantalla de notificaciones
 */
@Composable
private fun NotificationHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón de volver
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_logout), // TODO: Usar un icono de flecha a la izquierda
                contentDescription = "Back",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(24.dp)
            )
        }

        // Título
        Text(
            text = "Notification",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        // Icono de notificación (estático)
        IconButton(
            onClick = { /* No hace nada, es decorativo */ },
            modifier = Modifier.size(40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_campana),
                contentDescription = "Notifications",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

/**
 * Función auxiliar para obtener notificaciones de ejemplo
 */
private fun getExampleNotifications(): List<NotificationGroup> {
    return listOf(
        NotificationGroup(
            period = "Today",
            notifications = listOf(
                Notification(
                    id = 1,
                    type = NotificationType.REMINDER,
                    title = "Reminder!",
                    description = "Set up your automatic savings to meet your savings goal...",
                    timestamp = "17:00 - April 24"
                ),
                Notification(
                    id = 2,
                    type = NotificationType.NEW_UPDATE,
                    title = "New Update",
                    description = "Set up your automatic savings to meet your savings goal...",
                    timestamp = "17:00 - April 24"
                )
            )
        ),
        NotificationGroup(
            period = "Yesterday",
            notifications = listOf(
                Notification(
                    id = 3,
                    type = NotificationType.TRANSACTION,
                    title = "Transactions",
                    description = "A new transaction has been registered",
                    timestamp = "17:00 - April 24",
                    additionalInfo = "Groceries | Pantry | -$100,00"
                ),
                Notification(
                    id = 4,
                    type = NotificationType.REMINDER,
                    title = "Reminder!",
                    description = "Set up your automatic savings to meet your savings goal...",
                    timestamp = "17:00 - April 24"
                )
            )
        ),
        NotificationGroup(
            period = "This Weekend",
            notifications = listOf(
                Notification(
                    id = 5,
                    type = NotificationType.EXPENSE_RECORD,
                    title = "Expense Record",
                    description = "We recommend that you be more attentive to your finances.",
                    timestamp = "17:00 - April 24"
                ),
                Notification(
                    id = 6,
                    type = NotificationType.TRANSACTION,
                    title = "Transactions",
                    description = "A new transaction has been registered",
                    timestamp = "17:00 - April 24",
                    additionalInfo = "Food | Dinner | -$70,40"
                )
            )
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NotificationScreenPreview() {
    ParcialTP3Theme {
        NotificationScreen()
    }
}
