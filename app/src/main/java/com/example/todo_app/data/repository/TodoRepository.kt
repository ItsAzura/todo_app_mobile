package com.example.todo_app.data.repository

import com.example.todo_app.data.local.TokenStorage
import com.example.todo_app.data.remote.api.TodoAPI
import com.example.todo_app.data.remote.dto.TodoResponse
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val api: TodoAPI,
    private val tokenStorage: TokenStorage
) {

    private suspend fun getAuthToken(): String {
        val token = tokenStorage.getTokenSync()
            ?: throw IllegalStateException("No token found. User not logged in.")
        return "Bearer $token"
    }

    suspend fun getTodos(): List<TodoResponse> {
        return api.getTodos(getAuthToken())
    }

    suspend fun createTodo(title: String) {
        api.createTodo(getAuthToken(), mapOf("title" to title))
    }

    suspend fun updateTodo(id: String, title: String) {
        api.updateTodo(getAuthToken(), id, mapOf("title" to title))
    }

    suspend fun deleteTodo(id: String) {
        api.deleteTodo(getAuthToken(), id)
    }

    suspend fun markTodoDone(id: String) {
        api.markTodoDone(getAuthToken(), id)
    }
    
    suspend fun markTodoDoing(id: String) {
        api.markTodoDoing(getAuthToken(), id)
    }
}