package com.example.parcialtp3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialtp3.data.remote.dto.User
import com.example.parcialtp3.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            // Use mock user ID - in a real app, this would come from saved login session
            val userId = 1 // Mock user ID
            val result = authRepository.getUser(userId)

            _uiState.value = if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    ProfileUiState.Success(user)
                } else {
                    ProfileUiState.Error("User not found")
                }
            } else {
                ProfileUiState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun retryLoadProfile() {
        loadUserProfile()
    }

    fun logout() {
        authRepository.logout()
    }
}

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val user: User) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}
