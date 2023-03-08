package com.example.retrofitapp.domin

import com.example.retrofitapp.models.data.CreateTasksBodyData
import com.example.retrofitapp.models.data.Local.LocalStorage
import com.example.retrofitapp.models.data.LoginRequestBodyData
import com.example.retrofitapp.models.data.RegisterRequestBodyData
import com.example.retrofitapp.models.data.ResultData
import com.example.retrofitapp.retrofitapp.TodoApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MainRepository(private val api: TodoApi) {

    suspend fun getAllTasks() = flow {

        val token = LocalStorage().token

        val responce = api.getAllTasks("Bearer $token")

        if (responce.isSuccessful) {
            emit(ResultData.Success(responce.body()!!.payload))
        } else {
            emit(ResultData.Message(responce.message()))
        }
    } .catch {
        emit(ResultData.Error(it))
    }

    suspend fun registerApi(name: String, password: String, phone: String) = flow {
        val responce = api.registerApi(RegisterRequestBodyData(name, password, phone))

        if (responce.isSuccessful) {
            LocalStorage().token = responce.body()!!.payload.token
            emit(ResultData.Success(responce.body()!!.message))
        }else{
            emit(ResultData.Message(responce.message()))
        }
    }
    suspend fun loginApi(phone: String, password: String) = flow {
        val responce =
            api.loginApi(LoginRequestBodyData(password = password, phone = phone))

        if (responce.isSuccessful) {
            emit(ResultData.Success(responce.body()!!.payload.token))
        } else {
            emit(ResultData.Message(responce.message()))
        }
    }

    suspend fun createTask(description: String, task: String) = flow {
        val responce =
            api.createTask(CreateTasksBodyData(description = description, task = task), "Bearer ${LocalStorage().token}")

        if (responce.isSuccessful) {
            emit(ResultData.Success(responce.body()!!.payload.description))
        } else {
            emit(ResultData.Message(responce.message()))
        }
    }
}
