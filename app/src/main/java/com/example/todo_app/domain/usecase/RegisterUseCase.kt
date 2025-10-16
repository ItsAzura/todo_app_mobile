package com.example.todo_app.domain.usecase

import com.example.todo_app.domain.model.User
import com.example.todo_app.domain.repository.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(first: String, last: String, email: String, password: String): User {
        return repository.register(first, last, email, password)
    }
}
