package com.example.todo_app.data.remote.dto

data class TodoResponse(
    val id: String,
    val title: String,
    val status: String,
    val created_at: String,
    val updated_at: String
)