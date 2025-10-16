package com.example.todo_app.data.dto

data class UserProfileResponse(
    val data: UserProfileData
)

data class UserProfileData(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val phone: String,
    val avatar: String,
    val gender: String,
    val system_role: String,
    val status: String
)
