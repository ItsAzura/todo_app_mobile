package com.example.todo_app.data.dto

data class LoginResponse(
    val access_token: AccessToken
)

data class AccessToken(
    val token: String,
    val expire: Long
)