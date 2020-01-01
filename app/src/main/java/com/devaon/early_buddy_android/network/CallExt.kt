package com.devaon.early_buddy_android.network

import android.util.Log
import com.devaon.early_buddy_android.data.route.RouteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<BaseResponse<T>>.safeEnqueue(
    onError: (Throwable) -> Unit = errorStub,
    onSuccess: (T) -> Unit = {},
    onFail: (code: Int, message: String) -> Unit = {_,_ -> Unit},
    onAction: () -> Unit = {}
) {
    this.enqueue(object : Callback<BaseResponse<T>> {
        override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
            onError(t)
        }

        override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>) {
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    onSuccess(it)
                }
                onAction()
            }
            else {
                onFail(response.code(), response.message())
            }
        }
    })
}

private val errorStub: (Throwable) -> Unit = {
    Log.e("error is ", it.toString())
}

data class BaseResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T?
)