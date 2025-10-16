package com.example.todo_app.presentation.todo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val todos by viewModel.todos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    var newTodo by remember { mutableStateOf("") }

    // 🔹 Tải dữ liệu khi mở màn hình (chỉ chạy 1 lần)
    LaunchedEffect(Unit) {
        viewModel.loadTodos()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Ô nhập liệu
        OutlinedTextField(
            value = newTodo,
            onValueChange = { newTodo = it },
            label = { Text("New To-Do") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nút thêm mới
        Button(
            onClick = {
                if (newTodo.isNotEmpty()) {
                    viewModel.addTodo(newTodo)
                    newTodo = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hiển thị error nếu có
        error?.let { errorMessage ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    TextButton(
                        onClick = { viewModel.clearError() }
                    ) {
                        Text("Dismiss")
                    }
                }
            }
        }

        // Hiển thị loading hoặc danh sách
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(todos) { todo ->
                    TodoItem(
                        title = todo.title,
                        status = todo.status,
                        onDone = { viewModel.markDone(todo.id) },
                        onDoing = { viewModel.markDoing(todo.id) },
                        onDelete = { viewModel.deleteTodo(todo.id) }
                    )
                }
            }
        }
    }
}

// 🔹 Tách item ra riêng cho gọn, dễ tái sử dụng
@Composable
fun TodoItem(
    title: String,
    status: String,
    onDone: () -> Unit,
    onDoing: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (status) {
                "done" -> MaterialTheme.colorScheme.primaryContainer
                "doing" -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = if (status == "done") {
                        MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    } else {
                        MaterialTheme.typography.bodyLarge
                    }
                )
                Text(
                    text = status.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = when (status) {
                        "done" -> MaterialTheme.colorScheme.onPrimaryContainer
                        "doing" -> MaterialTheme.colorScheme.onSecondaryContainer
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row {
                if (status != "done") {
                    TextButton(onClick = onDone) { 
                        Text("✅ Done") 
                    }
                }
                if (status != "doing") {
                    TextButton(onClick = onDoing) { 
                        Text("🔄 Doing") 
                    }
                }
                TextButton(onClick = onDelete) { 
                    Text("🗑 Delete") 
                }
            }
        }
    }
}
