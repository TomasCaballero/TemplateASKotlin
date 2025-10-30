package com.example.parcialtp3.data.remote.dto

/**
 * Respuesta de GET /transactions
 */
data class TransactionResponse(
    val user_id: Int,
    val balance: Double,
    val income: Double,
    val expense: Double,
    val transactions: List<TransactionDto>
)
