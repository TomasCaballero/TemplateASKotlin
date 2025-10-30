package com.example.parcialtp3.data.remote.dto

/**
 * Respuesta de GET /users/{id} y POST /auth/create
 */
data class User(
    val id: Int,
    val email: String,
    val username: String,
    val password: String, // El API devuelve el password, ¡mala práctica!
    val name: Name,
    val address: Address,
    val phone: String
)
