package com.example.todo_app.domain.usecase

import com.example.todo_app.data.repository.TodoRepository

class TodoUseCases(private val repository: TodoRepository) {

    suspend fun getTodos() = repository.getTodos()

    suspend fun createTodo(title: String) = repository.createTodo(title)

    suspend fun updateTodo(id: String, title: String) = repository.updateTodo(id, title)

    suspend fun deleteTodo(id: String) = repository.deleteTodo(id)

    suspend fun markDone(id: String) = repository.markTodoDone(id)

    suspend fun markDoing(id: String) = repository.markTodoDoing(id)
}
