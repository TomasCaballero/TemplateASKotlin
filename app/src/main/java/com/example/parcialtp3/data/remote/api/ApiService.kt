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

    // ==================== Example Endpoints (for reference) ====================

    /**
     * GET request example
     * @GET annotation specifies the endpoint path
     */
    @GET("examples")
    suspend fun getExamples(): Response<List<ExampleDto>>

    /**
     * GET request with path parameter
     * @Path replaces {id} in the URL with the actual id value
     */
    @GET("examples/{id}")
    suspend fun getExampleById(@Path("id") id: Long): Response<ExampleDto>

    /**
     * GET request with query parameters
     * Example: /examples/search?query=kotlin&limit=10
     */
    @GET("examples/search")
    suspend fun searchExamples(
        @Query("query") searchQuery: String,
        @Query("limit") limit: Int = 20
    ): Response<List<ExampleDto>>

    /**
     * POST request example
     * @Body annotation sends the object as JSON in request body
     */
    @POST("examples")
    suspend fun createExample(@Body example: ExampleDto): Response<ExampleDto>

    /**
     * PUT request for updating
     */
    @PUT("examples/{id}")
    suspend fun updateExample(
        @Path("id") id: Long,
        @Body example: ExampleDto
    ): Response<ExampleDto>

    /**
     * DELETE request
     */
    @DELETE("examples/{id}")
    suspend fun deleteExample(@Path("id") id: Long): Response<Unit>

    /**
     * POST with headers example
     * @Header adds a header to the request
     */
    @Headers("Content-Type: application/json")
    @POST("examples/batch")
    suspend fun createMultipleExamples(
        @Body examples: List<ExampleDto>,
        @Header("Authorization") token: String
    ): Response<List<ExampleDto>>
}
