package com.example.parcialtp3.domain.repository

import com.example.parcialtp3.data.remote.dto.LoginRequest
import com.example.parcialtp3.data.remote.dto.LoginResponse
import com.example.parcialtp3.data.remote.dto.SignupRequest
import com.example.parcialtp3.data.remote.dto.User

/**
 * Interfaz del repositorio de autenticación
 * Define las operaciones de autenticación disponibles
 */
interface AuthRepository {

    /**
     * Realiza el login del usuario
     * @param username nombre de usuario o email
     * @param password contraseña del usuario
     * @return Result con LoginResponse o error
     */
    suspend fun login(username: String, password: String): Result<LoginResponse>

    /**
     * Registra un nuevo usuario
     * @param signupRequest datos del nuevo usuario
     * @return Result con User o error
     */
    suspend fun signup(signupRequest: SignupRequest): Result<User>

    /**
     * Obtiene la información del usuario por ID
     * @param userId ID del usuario
     * @return Result con User o error
     */
    suspend fun getUser(userId: Int): Result<User>

    /**
     * Cierra la sesión del usuario
     */
    fun logout()

    /**
     * Verifica si hay una sesión activa
     * @return true si hay sesión activa, false en caso contrario
     */
    fun isLoggedIn(): Boolean

    /**
     * Obtiene el token de autenticación guardado
     * @return El token o null si no existe
     */
    fun getToken(): String?
}
