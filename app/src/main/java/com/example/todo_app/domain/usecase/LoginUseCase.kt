package com.example.todo_app.domain.usecase

import com.example.todo_app.domain.model.User
import com.example.todo_app.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): User {
        return repository.login(email, password)
    }
}