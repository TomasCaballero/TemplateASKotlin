package com.example.parcialtp3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.parcialtp3.domain.model.Transaction
import com.example.parcialtp3.domain.model.TransactionIconType
import com.example.parcialtp3.domain.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel para la pantalla Home
 * Maneja el estado de balance, transacciones y estadísticas
 */
class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMockData()
    }

    /**
     * Cambia el tab seleccionado (Daily/Weekly/Monthly)
     */
    fun selectTab(tab: TimeTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    /**
     * Carga datos de ejemplo para la UI
     * TODO: Reemplazar con datos reales del repository
     */
    private fun loadMockData() {
        val mockTransactions = listOf(
            Transaction(
                id = 1,
                title = "Salary",
                category = "Monthly",
                amount = 4000.00,
                date = "April 30",
                time = "18:27",
                type = TransactionType.INCOME,
                iconType = TransactionIconType.SALARY
            ),
            Transaction(
                id = 2,
                title = "Groceries",
                category = "Pantry",
                amount = -100.00,
                date = "April 24",
                time = "17:00",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.GROCERIES
            ),
            Transaction(
                id = 3,
                title = "Rent",
                category = "Rent",
                amount = -674.40,
                date = "April 15",
                time = "8:30",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.RENT
            ),
            Transaction(
                id = 4,
                title = "Groceries",
                category = "Pantry",
                amount = -100.00,
                date = "April 24",
                time = "17:00",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.GROCERIES
            ),
            Transaction(
                id = 5,
                title = "Groceries",
                category = "Pantry",
                amount = -100.00,
                date = "April 24",
                time = "17:00",
                type = TransactionType.EXPENSE,
                iconType = TransactionIconType.GROCERIES
            ),
        )

        _uiState.value = _uiState.value.copy(
            totalBalance = 7783.00,
            totalExpense = -1187.40,
            expensePercentage = 30,
            expenseLimit = 20000.00,
            revenueLastWeek = 4000.00,
            foodLastWeek = -100.00,
            transactions = mockTransactions
        )
    }
}

/**
 * Estado de UI para la pantalla Home
 */
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

/**
 * Tabs de tiempo para filtrar transacciones
 */
enum class TimeTab {
    DAILY,
    WEEKLY,
    MONTHLY
}
