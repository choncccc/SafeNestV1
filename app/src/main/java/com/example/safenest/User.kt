package com.example.safenest

data class User(
    val id: Long,
    val username: String,
    val name: String,
    val password: String,
    val loginCounter: Int
)