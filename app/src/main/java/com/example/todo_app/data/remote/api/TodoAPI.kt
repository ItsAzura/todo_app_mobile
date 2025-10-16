package com.example.todo_app.data.remote.api

import com.example.todo_app.data.remote.dto.TodoResponse
import retrofit2.http.*

interface TodoAPI {

    // ✅ Lấy danh sách todos
    @GET("tasks/v1/tasks")
    suspend fun getTodos(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int = 200
    ): List<TodoResponse>

    // ✅ Tạo todo mới
    @POST("tasks/v1/tasks")
    suspend fun createTodo(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): TodoResponse

    // ✅ Cập nhật todo
    @PATCH("tasks/v1/tasks/{id}")
    suspend fun updateTodo(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): TodoResponse

    // ✅ Xóa todo
    @DELETE("tasks/v1/tasks/{id}")
    suspend fun deleteTodo(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

    // ✅ Đánh dấu hoàn thành
    @PATCH("tasks/v1/tasks/{id}/done")
    suspend fun markTodoDone(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): TodoResponse

    // ✅ Đánh dấu đang làm
    @PATCH("tasks/v1/tasks/{id}/doing")
    suspend fun markTodoDoing(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): TodoResponse
}
