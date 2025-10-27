package com.example.parcialtp3.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object for API responses
 *
 * @SerializedName maps JSON field names to Kotlin property names
 * This is useful when API uses snake_case and we want camelCase in Kotlin
 */
data class ExampleDto(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("created_at")
    val createdAt: Long,

    @SerializedName("is_completed")
    val isCompleted: Boolean = false,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("author")
    val author: AuthorDto? = null
)

/**
 * Nested DTO example for demonstrating complex JSON structures
 */
data class AuthorDto(
    @SerializedName("user_id")
    val userId: Long,

    @SerializedName("username")
    val username: String,

    @SerializedName("profile_image")
    val profileImage: String? = null
)
