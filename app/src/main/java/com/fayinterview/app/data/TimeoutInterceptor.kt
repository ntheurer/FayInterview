package com.fayinterview.app.data

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.SocketTimeoutException

class TimeoutInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (exception: SocketTimeoutException) {
            // Even though there was a timeout, we can retry the request retryAttempts times
            Log.w(
                "TimeoutInterceptor",
                "Call timed out"
            )
            Response.Builder()
                .request(chain.request())
                .protocol(chain.connection()?.protocol() ?: okhttp3.Protocol.HTTP_1_1)
                .code(408) // HTTP 408 Request Timeout
                .message("Request Timeout")
                .body(ResponseBody.create(
                    MediaType.parse("application/json"),
                    "{\"error\":\"Socket timeout\"}")
                )
                .build()
        }
    }
}
