package com.example.todo_app.domain.model

data class User(
    val token: String,
    val email: String? = null,
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val expire: Long? = null
)