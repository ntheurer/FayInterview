package com.fayinterview.app.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Service used to call Fay servers. Note: If this app gets bigger this could be broken up into
 * an AuthService (for sign in/up calls) and another (or multiple other) services for other calls
 */
interface FayService {
    @POST("/signin")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>
}
