package com.example.retrofitapp.di

import okhttp3.Interceptor
import okhttp3.Response

class TodoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = ""
        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }
}