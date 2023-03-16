package com.example.retrofitapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitapp.domin.MainRepository
import com.example.retrofitapp.models.data.ResultData
import com.example.retrofitapp.models.data.TaskData
import com.example.retrofitapp.retrofitapp.TodoApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.create

class TasksViewModel(private val repo: MainRepository): ViewModel() {

    val getAllTasksFlow = MutableSharedFlow<List<TaskData>>()
    val getMessageFlow = MutableSharedFlow<String>()
    val getErrorFlow = MutableSharedFlow<Throwable>()

    val getTaskFlow = MutableSharedFlow<String>()
    val getTaskMessageFlow = MutableSharedFlow<String>()
    val getTaskErrorFlow = MutableSharedFlow<Throwable>()

    suspend fun getAllTasks() {
        repo.getAllTasks().onEach {
            when(it) {
                is ResultData.Success -> {
                    getAllTasksFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    getMessageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    getTaskErrorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun createTasks(desc: String, task: String) {
        repo.createTask(desc, task).onEach {
            when(it) {
                is ResultData.Success ->{
                    getTaskFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    getTaskMessageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    getErrorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}


