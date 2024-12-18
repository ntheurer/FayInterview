package com.fayinterview.app.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException

class TimeoutInterceptor(
    private val retryAttempts: Int
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var lastException: SocketTimeoutException? = null
        for (i in 1..retryAttempts) {
            try {
                return chain.proceed(chain.request())
            } catch (exception: SocketTimeoutException) {
                // Even though there was a timeout, we can retry the request retryAttempts times
                Log.w(
                    "TimeoutInterceptor",
                    "Call timed out. Retry Attempts remaining: ${retryAttempts - i}"
                )
                lastException = exception
            }
        }
        throw lastException ?: RuntimeException("Failed to handle the request")
    }
}
