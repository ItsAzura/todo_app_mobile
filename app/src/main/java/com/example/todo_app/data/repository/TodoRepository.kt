package com.example.todo_app.data.repository

import com.example.todo_app.data.remote.api.TodoAPI
import com.example.todo_app.data.remote.dto.TodoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TodoRepository() {

    private val api: TodoAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.0.33:8080/") // 👈 base URL của bạn
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(TodoAPI::class.java)
    }

    suspend fun getTodos(): List<TodoResponse> {
        return api.getTodos()
    }

    suspend fun createTodo(title: String) {
        api.createTodo(mapOf("title" to title))
    }

    suspend fun updateTodo(id: String, title: String) {
        api.updateTodo(id, mapOf("title" to title))
    }

    suspend fun deleteTodo(id: String) {
        api.deleteTodo(id)
    }

    suspend fun markTodoDone(id: String) {
        api.markTodoDone(id)
    }
    suspend fun markTodoDoing(id: String) {
        api.markTodoDoing(id)
    }
}