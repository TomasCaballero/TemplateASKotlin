package com.example.parcialtp3.data.repository

import com.example.parcialtp3.data.local.dao.ExpenseDao
import com.example.parcialtp3.data.local.entities.ExpenseEntity
import com.example.parcialtp3.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return expenseDao.getAllExpenses()
    }

    override fun getExpensesByCategory(categoryName: String): Flow<List<ExpenseEntity>> {
        return expenseDao.getExpensesByCategory(categoryName)
    }

    override suspend fun getExpenseById(id: Long): ExpenseEntity? {
        return expenseDao.getExpenseById(id)
    }

    override suspend fun insertExpense(expense: ExpenseEntity): Long {
        return expenseDao.insertExpense(expense)
    }

    override suspend fun updateExpense(expense: ExpenseEntity) {
        expenseDao.updateExpense(expense)
    }

    override suspend fun deleteExpense(expense: ExpenseEntity) {
        expenseDao.deleteExpense(expense)
    }

    override suspend fun deleteExpensesByCategory(categoryName: String) {
        expenseDao.deleteExpensesByCategory(categoryName)
    }
}
