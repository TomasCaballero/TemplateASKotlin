package com.example.parcialtp3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialtp3.domain.model.ExampleModel
import com.example.parcialtp3.domain.model.Result
import com.example.parcialtp3.domain.repository.ExampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing Example data
 *
 * @HiltViewModel annotation tells Hilt to inject dependencies
 * @Inject constructor receives dependencies from Hilt
 */
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: ExampleRepository
) : ViewModel() {

    // UI State using StateFlow (reactive)
    private val _uiState = MutableStateFlow(ExampleUiState())
    val uiState: StateFlow<ExampleUiState> = _uiState.asStateFlow()

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        // Load data when ViewModel is created
        loadExamples()
    }

    /**
     * Load examples from local database
     */
    fun loadExamples() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getAllExamplesFromDb()
                .catch { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                }
                .collect { examples ->
                    _uiState.update {
                        it.copy(
                            examples = examples,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    /**
     * Sync examples from API to local database
     */
    fun syncExamples() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true) }

            when (val result = repository.syncExamples()) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isSyncing = false,
                            error = null
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isSyncing = false,
                            error = result.message
                        )
                    }
                }
                is Result.Loading -> {
                    // Already handled by isSyncing flag
                }
            }
        }
    }

    /**
     * Create a new example
     */
    fun createExample(title: String, description: String, imageUrl: String? = null) {
        viewModelScope.launch {
            val example = ExampleModel(
                title = title,
                description = description,
                createdAt = System.currentTimeMillis(),
                imageUrl = imageUrl
            )

            try {
                repository.insertExample(example)
                _uiState.update { it.copy(error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    /**
     * Update an example
     */
    fun updateExample(example: ExampleModel) {
        viewModelScope.launch {
            try {
                repository.updateExample(example)
                _uiState.update { it.copy(error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    /**
     * Delete an example
     */
    fun deleteExample(example: ExampleModel) {
        viewModelScope.launch {
            try {
                repository.deleteExample(example)
                _uiState.update { it.copy(error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    /**
     * Toggle completion status
     */
    fun toggleCompletion(example: ExampleModel) {
        val updated = example.copy(isCompleted = !example.isCompleted)
        updateExample(updated)
    }

    /**
     * Search examples
     */
    fun searchExamples(query: String) {
        _searchQuery.value = query

        viewModelScope.launch {
            repository.searchExamples(query)
                .catch { exception ->
                    _uiState.update { it.copy(error = exception.message) }
                }
                .collect { results ->
                    _uiState.update { it.copy(examples = results) }
                }
        }
    }

    /**
     * Clear error message
     */
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

/**
 * UI State data class
 * Represents the current state of the UI
 */
data class ExampleUiState(
    val examples: List<ExampleModel> = emptyList(),
    val isLoading: Boolean = false,
    val isSyncing: Boolean = false,
    val error: String? = null
)
