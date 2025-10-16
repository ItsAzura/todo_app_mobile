package com.example.todo_app.data.repository

import com.example.todo_app.data.local.TokenStorage
import com.example.todo_app.data.remote.api.TodoAPI
import com.example.todo_app.data.remote.dto.TodoResponse
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val api: TodoAPI,
    private val tokenStorage: TokenStorage
) {
    
    // Mock data storage
    private val mockTodos = mutableListOf<TodoResponse>()
    
    init {
        // Initialize with some mock data
        mockTodos.addAll(listOf(
            TodoResponse(
                id = "1",
                title = "Complete Android project",
                status = "doing",
                created_at = "2025-01-16T10:00:00Z",
                updated_at = "2025-01-16T10:00:00Z"
            ),
            TodoResponse(
                id = "2", 
                title = "Learn Kotlin Compose",
                status = "done",
                created_at = "2025-01-15T09:00:00Z",
                updated_at = "2025-01-15T15:30:00Z"
            ),
            TodoResponse(
                id = "3",
                title = "Setup CI/CD pipeline",
                status = "todo",
                created_at = "2025-01-14T14:00:00Z",
                updated_at = "2025-01-14T14:00:00Z"
            ),
            TodoResponse(
                id = "4",
                title = "Write unit tests",
                status = "todo",
                created_at = "2025-01-13T11:00:00Z",
                updated_at = "2025-01-13T11:00:00Z"
            ),
            TodoResponse(
                id = "5",
                title = "Deploy to production",
                status = "todo",
                created_at = "2025-01-12T16:00:00Z",
                updated_at = "2025-01-12T16:00:00Z"
            )
        ))
    }

    private suspend fun getAuthToken(): String {
        val token = tokenStorage.getTokenSync()
            ?: throw IllegalStateException("No token found. User not logged in.")
        return "Bearer $token"
    }

    suspend fun getTodos(): List<TodoResponse> {
        // Simulate network delay
        delay(1000)
        return mockTodos.toList()
    }

    suspend fun createTodo(title: String) {
        // Simulate network delay
        delay(500)
        val newTodo = TodoResponse(
            id = UUID.randomUUID().toString(),
            title = title,
            status = "todo",
            created_at = java.time.Instant.now().toString(),
            updated_at = java.time.Instant.now().toString()
        )
        mockTodos.add(newTodo)
        
        // Uncomment to use real API
        // api.createTodo(getAuthToken(), mapOf("title" to title))
    }

    suspend fun updateTodo(id: String, title: String) {
        // Simulate network delay
        delay(500)
        val index = mockTodos.indexOfFirst { it.id == id }
        if (index != -1) {
            mockTodos[index] = mockTodos[index].copy(
                title = title,
                updated_at = java.time.Instant.now().toString()
            )
        }
        
        // Uncomment to use real API
        // api.updateTodo(getAuthToken(), id, mapOf("title" to title))
    }

    suspend fun deleteTodo(id: String) {
        // Simulate network delay
        delay(500)
        mockTodos.removeAll { it.id == id }
        
        // Uncomment to use real API
        // api.deleteTodo(getAuthToken(), id)
    }

    suspend fun markTodoDone(id: String) {
        // Simulate network delay
        delay(500)
        val index = mockTodos.indexOfFirst { it.id == id }
        if (index != -1) {
            mockTodos[index] = mockTodos[index].copy(
                status = "done",
                updated_at = java.time.Instant.now().toString()
            )
        }
        
        // Uncomment to use real API
        // api.markTodoDone(getAuthToken(), id)
    }
    
    suspend fun markTodoDoing(id: String) {
        // Simulate network delay
        delay(500)
        val index = mockTodos.indexOfFirst { it.id == id }
        if (index != -1) {
            mockTodos[index] = mockTodos[index].copy(
                status = "doing",
                updated_at = java.time.Instant.now().toString()
            )
        }
        
        // Uncomment to use real API
        // api.markTodoDoing(getAuthToken(), id)
    }
}