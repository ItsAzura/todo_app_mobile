package com.example.todo_app.presentation.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back",
            fontSize = 28.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        TextField(
            value = email, 
            onValueChange = { email = it }, 
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextField(
            value = password, 
            onValueChange = { password = it }, 
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state !is LoginUiState.Loading
        ) {
            when (state) {
                is LoginUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier.padding(8.dp)
                )
                else -> Text("Sign In")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text("Don't have an account? ")
            androidx.compose.material3.OutlinedButton(onClick = onNavigateToRegister) {
                Text("Sign Up")
            }
        }

        when (state) {
            is LoginUiState.Loading -> CircularProgressIndicator()
            is LoginUiState.Success -> {
                onLoginSuccess()
                Text(
                    text = "Login successful!",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            }
            is LoginUiState.Error -> Text(
                text = "Error: ${(state as LoginUiState.Error).message}",
                color = androidx.compose.material3.MaterialTheme.colorScheme.error
            )
            else -> {}
        }
    }
}