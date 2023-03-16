package com.example.retrofitapp.di

import com.example.retrofitapp.ViewModel.MainViewModel
import com.example.retrofitapp.ViewModel.TasksViewModel
import com.example.retrofitapp.domin.MainRepository
import com.example.retrofitapp.retrofitapp.TodoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<MainRepository> {
        MainRepository(api= get())
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single<TodoApi> {
        provideTodoApi(retrofit = get())
    }

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://todo.paydali.uz")
            .client(get())
            .build()
    }
    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }
}

val viewModelModule = module {
       viewModel<MainViewModel> {
           MainViewModel(repo = get())
       }
    viewModel<TasksViewModel>{
        TasksViewModel(repo = get())
    }
}
fun provideTodoApi(retrofit: Retrofit) = retrofit.create(TodoApi::class.java)

