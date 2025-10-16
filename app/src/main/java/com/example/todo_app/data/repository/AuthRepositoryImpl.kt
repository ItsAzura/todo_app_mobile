package com.example.todo_app.data.repository

import com.example.todo_app.data.dto.LoginRequest
import com.example.todo_app.data.dto.RegisterRequest
import com.example.todo_app.data.local.TokenStorage
import com.example.todo_app.data.remote.api.AuthApi
import com.example.todo_app.domain.model.User
import com.example.todo_app.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenStorage: TokenStorage
) : AuthRepository {
    
    override suspend fun login(email: String, password: String): User {
        val response = api.login(LoginRequest(email, password))
        val token = response.access_token.token
        val expire = response.access_token.expire
        tokenStorage.saveToken(token, email)
        return User(token = token, email = email, expire = expire)
    }

    override suspend fun register(first: String, last: String, email: String, password: String): User {
        val response = api.register(RegisterRequest(first, last, email, password))
        
        // Register chỉ trả về data: true, không có token
        // Cần gọi login sau khi register thành công để lấy token
        if (response.data) {
            return login(email, password)
        } else {
            throw IllegalStateException("Registration failed")
        }
    }

    override suspend fun logout() {
        tokenStorage.clearToken()
    }

    override suspend fun isLoggedIn(): Boolean {
        val token = tokenStorage.getTokenSync()
        return !token.isNullOrEmpty()
    }

    override suspend fun getUserProfile(): User {
        val token = tokenStorage.getTokenSync()
            ?: throw IllegalStateException("No token found. User not logged in.")
        
        val response = api.getUserProfile("Bearer $token")
        return User(
            token = token,
            email = response.email,
            id = response.id,
            firstName = response.first_name,
            lastName = response.last_name,
            createdAt = response.created_at,
            updatedAt = response.updated_at
        )
    }
}