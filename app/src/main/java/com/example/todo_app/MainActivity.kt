package com.example.todo_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.todo_app.presentation.todo.TodoScreen
import com.example.todo_app.ui.theme.Todo_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Todo_appTheme {
                Scaffold { it ->
                    TodoScreen(modifier = Modifier.padding(it))
                }

            }
        }
    }
}
