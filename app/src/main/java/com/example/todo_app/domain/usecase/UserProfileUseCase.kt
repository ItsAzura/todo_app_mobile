package com.example.todo_app.domain.usecase

import com.example.todo_app.domain.model.User
import com.example.todo_app.domain.repository.AuthRepository

class UserProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): User {
        return repository.getUserProfile()
    }
}
