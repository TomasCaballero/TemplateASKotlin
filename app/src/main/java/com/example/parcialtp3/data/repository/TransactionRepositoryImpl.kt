package com.example.parcialtp3.data.repository

import com.example.parcialtp3.data.remote.api.ApiService
import com.example.parcialtp3.data.remote.dto.toDomain
import com.example.parcialtp3.domain.model.TransactionData
import com.example.parcialtp3.domain.repository.TransactionRepository
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TransactionRepository {

    override suspend fun getTransactions(): Result<TransactionData> {
        return try {
            val response = apiService.getTransactions(apiKey = "123456789")

            if (response.isSuccessful && response.body() != null) {
                val transactionResponse = response.body()!!

                val domainTransactions = transactionResponse.transactions.map { it.toDomain() }

                val transactionData = TransactionData(
                    userId = transactionResponse.user_id,
                    balance = transactionResponse.balance,
                    income = transactionResponse.income,
                    expense = transactionResponse.expense,
                    transactions = domainTransactions
                )

                Result.success(transactionData)
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
