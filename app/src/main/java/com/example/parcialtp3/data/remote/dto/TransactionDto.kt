package com.example.parcialtp3.data.remote.dto

data class TransactionDto(
    val transaction_id: String,
    val date: String,
    val description: String,
    val amount: Double,
    val currency: String,
    val type: String,
    val subtype: String
)
