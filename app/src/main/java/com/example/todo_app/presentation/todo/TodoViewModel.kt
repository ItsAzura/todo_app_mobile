package com.example.todo_app.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app.data.repository.TodoRepository
import com.example.todo_app.data.remote.dto.TodoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {

    private val repository = TodoRepository()

    private val _todos = MutableStateFlow<List<TodoResponse>>(emptyList())
    val todos: StateFlow<List<TodoResponse>> = _todos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _todos.value = repository.getTodos()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.createTodo(title)
            loadTodos()
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.deleteTodo(id)
            loadTodos()
        }
    }

    fun markDone(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.markTodoDone(id)
            loadTodos()
        }
    }
}
