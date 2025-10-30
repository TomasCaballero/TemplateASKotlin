package com.example.parcialtp3.data.remote.dto

/**
 * Body para POST /auth/login
 */
data class LoginRequest(
    val username: String,
    val password: String
)
