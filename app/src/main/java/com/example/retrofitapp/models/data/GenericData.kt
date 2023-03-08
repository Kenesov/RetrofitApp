package com.example.retrofitapp.models.data

data class GenericData<out T>(
val success: Boolean,
val code: Int,
val message: String,
val payload: T
)
