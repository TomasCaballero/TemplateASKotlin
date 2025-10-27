package com.example.parcialtp3.domain.model

/**
 * Domain model representing business logic data
 *
 * This is separate from:
 * - ExampleEntity (database layer)
 * - ExampleDto (network layer)
 *
 * This separation allows us to change database or API structure
 * without affecting the UI layer
 */
data class ExampleModel(
    val id: Long = 0,
    val title: String,
    val description: String,
    val createdAt: Long,
    val isCompleted: Boolean = false,
    val imageUrl: String? = null,
    val authorName: String? = null
)

/**
 * Example of using Generics
 * Generic Result wrapper for handling success/error states
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String, val exception: Exception? = null) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

/**
 * Extension function to convert Result to nullable data
 */
fun <T> Result<T>.getDataOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        is Result.Error -> null
        is Result.Loading -> null
    }
}
