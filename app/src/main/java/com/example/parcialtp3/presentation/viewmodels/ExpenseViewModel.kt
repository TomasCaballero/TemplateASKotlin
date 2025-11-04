package com.example.parcialtp3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialtp3.data.local.entities.ExpenseEntity
import com.example.parcialtp3.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expenses: StateFlow<List<ExpenseEntity>> = _expenses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun loadExpensesByCategory(categoryName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getExpensesByCategory(categoryName)
                .catch { e ->
                    _error.value = e.message
                    _isLoading.value = false
                }
                .collect { expenseList ->
                    _expenses.value = expenseList
                    _isLoading.value = false
                }
        }
    }

    fun saveExpense(
        categoryName: String,
        title: String,
        amount: Double,
        date: Long,
        message: String?
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _saveSuccess.value = false

                val expense = ExpenseEntity(
                    categoryName = categoryName,
                    title = title,
                    amount = amount,
                    date = date,
                    message = message
                )

                repository.insertExpense(expense)
                _saveSuccess.value = true
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.message
                _isLoading.value = false
            }
        }
    }

    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }

    fun clearError() {
        _error.value = null
    }
}
