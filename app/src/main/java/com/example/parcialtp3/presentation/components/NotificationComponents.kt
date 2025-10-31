package com.example.parcialtp3.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcialtp3.R
import com.example.parcialtp3.domain.model.Notification
import com.example.parcialtp3.domain.model.NotificationGroup
import com.example.parcialtp3.domain.model.NotificationType
import com.example.parcialtp3.ui.theme.FinGreenCard
import com.example.parcialtp3.ui.theme.FinIconBlue
import com.example.parcialtp3.ui.theme.ParcialTP3Theme

/**
 * Componente para mostrar un grupo de notificaciones
 */
@Composable
fun NotificationGroupComponent(
    group: NotificationGroup,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Título del grupo
        Text(
            text = group.period,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Lista de notificaciones
        Column {
            group.notifications.forEachIndexed { index, notification ->
                NotificationItem(
                    notification = notification,
                    modifier = Modifier.fillMaxWidth()
                )

                if (index < group.notifications.size - 1) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

/**
 * Componente individual para mostrar una notificación
 */
@Composable
fun NotificationItem(
    notification: Notification,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Icono circular con fondo verde
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(FinGreenCard),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = getNotificationIcon(notification.type)),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(24.dp)
            )
        }

        // Contenido de la notificación
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = notification.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = notification.description,
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 18.sp
            )

            // Información adicional para transacciones
            notification.additionalInfo?.let { info ->
                Text(
                    text = info,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
            }
        }

        // Timestamp
        Text(
            text = notification.timestamp,
            fontSize = 12.sp,
            color = FinIconBlue,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

/**
 * Obtiene el icono correspondiente al tipo de notificación
 */
private fun getNotificationIcon(type: NotificationType): Int {
    return when (type) {
        NotificationType.REMINDER -> R.drawable.home_campana
        NotificationType.NEW_UPDATE -> R.drawable.home_income // Estrella (usando income temporalmente)
        NotificationType.TRANSACTION -> R.drawable.home_expense
        NotificationType.EXPENSE_RECORD -> R.drawable.navbar_analysis
    }
}

// Previews
@Preview(showBackground = true)
@Composable
fun NotificationItemPreview() {
    ParcialTP3Theme {
        Column(modifier = Modifier.padding(16.dp)) {
            NotificationItem(
                notification = Notification(
                    id = 1,
                    type = NotificationType.REMINDER,
                    title = "Reminder!",
                    description = "Set up your automatic savings to meet your savings goal...",
                    timestamp = "17:00 - April 24"
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationItemWithInfoPreview() {
    ParcialTP3Theme {
        Column(modifier = Modifier.padding(16.dp)) {
            NotificationItem(
                notification = Notification(
                    id = 2,
                    type = NotificationType.TRANSACTION,
                    title = "Transactions",
                    description = "A new transaction has been registered",
                    timestamp = "17:00 - April 24",
                    additionalInfo = "Groceries | Pantry | -$100,00"
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationGroupPreview() {
    ParcialTP3Theme {
        Column(modifier = Modifier.padding(16.dp)) {
            NotificationGroupComponent(
                group = NotificationGroup(
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
                )
            )
        }
    }
}
