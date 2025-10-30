package com.example.parcialtp3.domain.model

/**
 * Modelo de dominio para transacciones financieras
 */
data class Transaction(
    val id: Long = 0L,
    val title: String,
    val category: String,
    val amount: Double,
    val date: String,
    val time: String,
    val type: TransactionType,
    val iconType: TransactionIconType
)

/**
 * Tipo de transacción (ingreso o gasto)
 */
enum class TransactionType {
    INCOME,
    EXPENSE
}

/**
 * Tipo de icono para la transacción
 */
enum class TransactionIconType {
    SALARY,
    GROCERIES,
    RENT,
    TRANSFER,
    ENTERTAINMENT,
    BILLS,
    SHOPPING,
    FOOD,
    TRANSPORT,
    OTHER
}
