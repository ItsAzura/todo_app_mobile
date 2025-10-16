package com.example.todo_app.di

import com.example.todo_app.data.local.TokenStorage
import com.example.todo_app.data.remote.api.AuthApi
import com.example.todo_app.data.repository.AuthRepositoryImpl
import com.example.todo_app.domain.repository.AuthRepository
import com.example.todo_app.domain.usecase.LoginUseCase
import com.example.todo_app.domain.usecase.RegisterUseCase
import com.example.todo_app.domain.usecase.UserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://10.0.0.33:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    fun provideAuthRepository(api: AuthApi, tokenStorage: TokenStorage): AuthRepository =
        AuthRepositoryImpl(api, tokenStorage)

    @Provides
    fun provideLoginUseCase(repo: AuthRepository) = LoginUseCase(repo)

    @Provides
    fun provideRegisterUseCase(repo: AuthRepository) = RegisterUseCase(repo)

    @Provides
    fun provideUserProfileUseCase(repo: AuthRepository) = UserProfileUseCase(repo)
}
