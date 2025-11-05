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

// Maneja el estado de balance, transacciones y estadísticas
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    // Cambia el tab seleccionado (Daily/Weekly/Monthly)

    fun selectTab(tab: TimeTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = transactionRepository.getTransactions()

            result.fold(
                onSuccess = { transactionData ->
                    _uiState.value = _uiState.value.copy(
                        totalBalance = transactionData.balance,
                        totalExpense = transactionData.expense,
                        expensePercentage = 30,
                        expenseLimit = 20000.00,
                        revenueLastWeek = 4000.00,
                        foodLastWeek = -100.00,
                        transactions = transactionData.transactions,
                        isLoading = false,
                        error = null
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Error"
                    )
                }
            )
        }
    }

    fun retryLoadTransactions() {
        loadTransactions()
    }
}

data class HomeUiState(
    val totalBalance: Double = 0.0,
    val totalExpense: Double = 0.0,
    val expensePercentage: Int = 0,
    val expenseLimit: Double = 0.0,
    val revenueLastWeek: Double = 0.0,
    val foodLastWeek: Double = 0.0,
    val transactions: List<Transaction> = emptyList(),
    val selectedTab: TimeTab = TimeTab.MONTHLY,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class TimeTab {
    DAILY,
    WEEKLY,
    MONTHLY
}
