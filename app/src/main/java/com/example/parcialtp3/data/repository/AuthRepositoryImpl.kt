package com.example.parcialtp3.data.repository

import com.example.parcialtp3.data.local.TokenManager
import com.example.parcialtp3.data.remote.api.ApiService
import com.example.parcialtp3.data.remote.dto.LoginRequest
import com.example.parcialtp3.data.remote.dto.LoginResponse
import com.example.parcialtp3.data.remote.dto.SignupRequest
import com.example.parcialtp3.data.remote.dto.User
import com.example.parcialtp3.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementación del repositorio de autenticación
 * Maneja las llamadas a la API y el almacenamiento del token
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                // Guardar el token de forma segura
                tokenManager.saveToken(loginResponse.token)
                Result.success(loginResponse)
            } else {
                Result.failure(Exception("Error en el login: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.message}", e))
        }
    }

    override suspend fun signup(signupRequest: SignupRequest): Result<User> {
        return try {
            val response = apiService.createUser(signupRequest)

            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!
                // Guardar el ID del usuario
                tokenManager.saveUserId(user.id)
                Result.success(user)
            } else {
                Result.failure(Exception("Error en el registro: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.message}", e))
        }
    }

    override suspend fun getUser(userId: Int): Result<User> {
        return try {
            val response = apiService.getUser(userId)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener usuario: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de red: ${e.message}", e))
        }
    }

    override fun logout() {
        tokenManager.clearToken()
    }

    override fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }

    override fun getToken(): String? {
        return tokenManager.getToken()
    }
}
