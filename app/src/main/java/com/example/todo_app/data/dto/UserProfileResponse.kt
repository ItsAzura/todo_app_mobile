package com.example.todo_app.data.dto

data class UserProfileResponse(
    val id: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val created_at: String,
    val updated_at: String
)
