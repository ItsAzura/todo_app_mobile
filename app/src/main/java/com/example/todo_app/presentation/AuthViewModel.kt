package com.example.todo_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_app.data.local.TokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState(isLoggedIn = false, userEmail = null))
    val authState: StateFlow<AuthState> = _authState

    init {
        observeAuthState()
    }

    private fun observeAuthState() {
        viewModelScope.launch {
            combine(
                tokenStorage.isLoggedIn,
                tokenStorage.userEmail
            ) { isLoggedIn, userEmail ->
                AuthState(isLoggedIn = isLoggedIn, userEmail = userEmail)
            }.collect { state ->
                _authState.value = state
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenStorage.clearToken()
        }
    }
}

data class AuthState(
    val isLoggedIn: Boolean,
    val userEmail: String?
)
