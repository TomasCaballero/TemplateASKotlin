package com.example.parcialtp3.domain.model

/**
 * Modelo de dominio para una notificación
 */
data class Notification(
    val id: Long,
    val type: NotificationType,
    val title: String,
    val description: String,
    val timestamp: String, // Formato: "HH:mm - MMM dd"
    val additionalInfo: String? = null // Info adicional para transacciones
)

/**
 * Tipos de notificación con sus iconos asociados
 */
enum class NotificationType {
    REMINDER,       // Campana
    NEW_UPDATE,     // Estrella
    TRANSACTION,    // Dinero
    EXPENSE_RECORD  // Gráfico
}

/**
 * Grupo de notificaciones por período de tiempo
 */
data class NotificationGroup(
    val period: String, // "Today", "Yesterday", "This Weekend"
    val notifications: List<Notification>
)
