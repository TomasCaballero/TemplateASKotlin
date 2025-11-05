package com.example.parcialtp3.domain.model

data class TransactionData(
    val userId: Int,
    val balance: Double,
    val income: Double,
    val expense: Double,
    val transactions: List<Transaction>
)