package com.example.parcialtp3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState.Loading)
    val uiState: StateFlow<TransactionUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = TransactionUiState.Loading

            val result = transactionRepository.getTransactions()

            _uiState.value = if (result.isSuccess) {
                val data = result.getOrNull()
                if (data != null) {
                    val transactionsByMonth = data.transactions.groupBy { transaction ->
                        transaction.date.split(" ").firstOrNull() ?: "Unknown"
                    }

                    TransactionUiState.Success(
                        totalBalance = data.balance,
                        totalIncome = data.income,
                        totalExpense = data.expense,
                        transactionsByMonth = transactionsByMonth
                    )
                } else {
                    TransactionUiState.Error("No transaction data available")
                }
            } else {
                TransactionUiState.Error(
                    result.exceptionOrNull()?.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun retry() {
        loadTransactions()
    }
}

sealed class TransactionUiState {
    object Loading : TransactionUiState()
    data class Success(
        val totalBalance: Double,
        val totalIncome: Double,
        val totalExpense: Double,
        val transactionsByMonth: Map<String, List<Transaction>>
    ) : TransactionUiState()
    data class Error(val message: String) : TransactionUiState()
}
