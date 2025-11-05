package com.example.parcialtp3.data.remote.api

import com.example.parcialtp3.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API Service interface
 *
 * Define all API endpoints here
 * Retrofit will generate the implementation using KSP
 */
interface ApiService {

    // ==================== Authentication Endpoints ====================

    /**
     * Endpoint: auth/login (Registro de Cliente)
     * Método: POST
     */
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    /**
     * Endpoint: auth/create (Registro de Usuario)
     * Método: POST
     */
    @POST("auth/create")
    suspend fun createUser(
        @Body signupRequest: SignupRequest
    ): Response<User>

    /**
     * Endpoint: users/{id} (Ver información del cliente)
     * Método: GET
     * Requiere Header: x-api-key
     */
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") userId: Int,
        @Header("x-api-key") apiKey: String = "123456789" // API Key fija
    ): Response<User>

    /**
     * Endpoint: transactions (Devolver las transacciones)
     * Método: GET
     * Requiere Header: x-api-key
     */
    @GET("transactions")
    suspend fun getTransactions(
        @Header("x-api-key") apiKey: String = "123456789" // API Key fija
    ): Response<TransactionResponse>
}
