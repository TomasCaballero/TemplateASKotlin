package com.example.parcialtp3.domain.repository

import com.example.parcialtp3.data.local.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    fun getExpensesByCategory(categoryName: String): Flow<List<ExpenseEntity>>
    suspend fun getExpenseById(id: Long): ExpenseEntity?
    suspend fun insertExpense(expense: ExpenseEntity): Long
    suspend fun updateExpense(expense: ExpenseEntity)
    suspend fun deleteExpense(expense: ExpenseEntity)
    suspend fun deleteExpensesByCategory(categoryName: String)
}
