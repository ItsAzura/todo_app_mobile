package com.example.todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo_app.presentation.AuthViewModel
import com.example.todo_app.presentation.MainScreen
import com.example.todo_app.presentation.auth.AuthNavigation
import com.example.todo_app.presentation.todo.TodoScreen
import com.example.todo_app.presentation.todo.TodoViewModel
import com.example.todo_app.presentation.user.UserProfileScreen
import com.example.todo_app.presentation.user.UserProfileViewModel
import com.example.todo_app.ui.theme.Todo_appTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Todo_appTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp()
                }

            }
        }
    }
}

@Composable
fun TodoApp(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (authState.isLoggedIn) "main" else "auth"
    ) {
        composable("auth") {
            AuthNavigation(
                onAuthSuccess = {
                    navController.navigate("main") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }

        composable("main") {
            MainScreen(
                viewModel = authViewModel,
                onNavigateToTodos = {
                    navController.navigate("todos")
                },
                onNavigateToProfile = {
                    navController.navigate("profile")
                },
                onLogout = {
                    navController.navigate("auth") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }

        composable("todos") {
            val todoViewModel: TodoViewModel = hiltViewModel()
            TodoScreen(viewModel = todoViewModel)
        }

        composable("profile") {
            val userProfileViewModel: UserProfileViewModel = hiltViewModel()
            UserProfileScreen(
                viewModel = userProfileViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    navController.navigate("auth") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
    }
}
