package com.example.todo_app.data.remote.api

import com.example.todo_app.data.remote.dto.TodoResponse
import retrofit2.http.*

interface TodoAPI {

    // ✅ Lấy danh sách todos
    @GET("tasks/v1/tasks")
    suspend fun getTodos(
        @Query("limit") limit: Int = 200
    ): List<TodoResponse>

    // ✅ Tạo todo mới
    @POST("tasks/v1/tasks")
    suspend fun createTodo(
        @Body body: Map<String, String>
    ): TodoResponse

    // ✅ Cập nhật todo
    @PATCH("tasks/v1/tasks/{id}")
    suspend fun updateTodo(
        @Path("id") id: String,
        @Body body: Map<String, String>
    ): TodoResponse

    // ✅ Xóa todo
    @DELETE("tasks/v1/tasks/{id}")
    suspend fun deleteTodo(
        @Path("id") id: String
    )

    // ✅ Đánh dấu hoàn thành
    @PATCH("tasks/v1/tasks/{id}/done")
    suspend fun markTodoDone(
        @Path("id") id: String
    ): TodoResponse

    // ✅ Đánh dấu đang làm
    @PATCH("tasks/v1/tasks/{id}/doing")
    suspend fun markTodoDoing(
        @Path("id") id: String
    ): TodoResponse
}
