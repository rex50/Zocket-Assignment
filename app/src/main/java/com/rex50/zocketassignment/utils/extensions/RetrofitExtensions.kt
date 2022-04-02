package com.rex50.zocketassignment.utils.extensions

import com.rex50.zocketassignment.utils.Result
import retrofit2.Response

fun <Type: Any> Response<Type>.toResult(): Result<Type> {
    return if(isSuccessful && body() != null) {
        Result.Success(body()!!)
    } else {
        Result.Failure(Exception(message()))
    }
}