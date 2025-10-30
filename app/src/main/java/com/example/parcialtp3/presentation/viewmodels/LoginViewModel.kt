package com.example.parcialtp3.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parcialtp3.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para la pantalla de Login
 * Maneja el estado y la lógica de autenticación
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Realiza el login del usuario
     */
    fun login(username: String, password: String) {
        // Validación básica
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Por favor, completa todos los campos")
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val result = authRepository.login(username, password)

            _uiState.value = if (result.isSuccess) {
                val token = result.getOrNull()?.token
                LoginUiState.Success(token ?: "")
            } else {
                LoginUiState.Error(result.exceptionOrNull()?.message ?: "Error desconocido")
            }
        }
    }

    /**
     * Resetea el estado del UI a Idle
     */
    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }

    /**
     * Verifica si el usuario ya está logueado
     */
    fun checkLoginStatus(): Boolean {
        return authRepository.isLoggedIn()
    }
}

/**
 * Estados posibles del UI de Login
 */
sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val token: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}
