package com.example.parcialtp3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialtp3.data.remote.dto.SignupRequest
import com.example.parcialtp3.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Idle)
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun signup(
        fullName: String,
        email: String,
        mobileNumber: String,
        dateOfBirth: String,
        password: String,
        confirmPassword: String
    ) {

        viewModelScope.launch {
            _uiState.value = SignupUiState.Loading

            val signupRequest = SignupRequest(
                id = (1..10000).random(),
                username = fullName,
                email = email,
                password = password
            )

            val result = authRepository.signup(signupRequest)

            _uiState.value = if (result.isSuccess) {
                val user = result.getOrNull()
                SignupUiState.Success(user?.username ?: fullName)
            } else {
                SignupUiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    fun resetState() {
        _uiState.value = SignupUiState.Idle
    }
}

sealed class SignupUiState {
    object Idle : SignupUiState()
    object Loading : SignupUiState()
    data class Success(val userName: String) : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}
