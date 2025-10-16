package com.example.todo_app.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app.data.remote.dto.TodoResponse
import com.example.todo_app.domain.usecase.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases
) : ViewModel() {

    private val _todos = MutableStateFlow<List<TodoResponse>>(emptyList())
    val todos: StateFlow<List<TodoResponse>> = _todos

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _todos.value = todoUseCases.getTodos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load todos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addTodo(title: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                todoUseCases.createTodo(title)
                loadTodos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to create todo"
                _isLoading.value = false
            }
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                todoUseCases.deleteTodo(id)
                loadTodos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete todo"
                _isLoading.value = false
            }
        }
    }

    fun markDone(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                todoUseCases.markDone(id)
                loadTodos()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to mark todo as done"
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
