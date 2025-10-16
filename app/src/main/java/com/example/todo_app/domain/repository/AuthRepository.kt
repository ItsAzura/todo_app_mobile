package com.example.todo_app.domain.repository

import com.example.todo_app.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): User
    suspend fun register(first: String, last: String, email: String, password: String): User
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
    suspend fun getUserProfile(): User
}