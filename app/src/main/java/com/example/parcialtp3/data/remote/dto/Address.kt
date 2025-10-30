package com.example.parcialtp3.data.remote.dto

data class Address(
    val geolocation: Geolocation,
    val city: String,
    val street: String,
    val number: Int,
    val zipcode: String
)
