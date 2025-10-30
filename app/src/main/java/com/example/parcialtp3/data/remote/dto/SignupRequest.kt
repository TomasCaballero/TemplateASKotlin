package com.example.parcialtp3.data.remote.dto

/**
 * Body para POST /auth/create
 */
data class SignupRequest(
    val id: Int, // El API pide un ID, aunque es inusual para un create
    val username: String,
    val email: String,
    val password: String
)
