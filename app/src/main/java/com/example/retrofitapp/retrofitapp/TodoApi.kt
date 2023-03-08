package com.example.retrofitapp.retrofitapp

import com.example.retrofitapp.models.data.*
import com.example.retrofitapp.models.data.Local.RegisterResponceData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TodoApi {

    @GET("/api/tasks")
    suspend fun getAllTasks(@Header("Authorization") token: String) : Response<GenericData<List<TaskData>>>

    @POST("/api/register")
    suspend fun registerApi(@Body body: RegisterRequestBodyData) : Response<GenericData<RegisterResponceData>>

    @POST("/api/login")
    suspend fun loginApi(@Body body: LoginRequestBodyData): Response<GenericData<RegisterResponceData>>

    @POST("/api/tasks")
    suspend fun createTask(@Body body: CreateTasksBodyData, @Header("Authorization") token: String) : Response<GenericData<TaskData>>

}