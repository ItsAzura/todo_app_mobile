package com.example.todo_app.data.remote.api

import com.example.todo_app.data.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/v1/authenticate")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/auth/v1/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("/user/v1/profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileResponse
}