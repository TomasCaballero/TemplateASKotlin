package com.example.parcialtp3.domain.repository

import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.model.TransactionData


interface TransactionRepository {
    suspend fun getTransactions(): Result<TransactionData>
}
