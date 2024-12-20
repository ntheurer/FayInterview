package com.fayinterview.app.data

import com.fayinterview.app.ui.appointments.AppointmentInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository used to call FayService. Note: If this app gets bigger this could be broken up into
 * an AuthRepository (for sign in/up calls) and another (or multiple other) repositories
 */
@Singleton
class FayRepository @Inject constructor(
    private val fayService: FayService,
    private val tokenManager: TokenManager
) {

    /**
     * Calls the endpoint to authenticate the user
     * @return error code or null if no error
     */
    suspend fun signIn(
        username: String,
        password: String
    ): Int? {
        val result = fayService.signIn(
            request = SignInRequest(
                username = username,
                password = password
            )
        )
        return when {
            result.isSuccessful -> {
                result.body()?.let {
                    tokenManager.saveToken(it.token)
                }
                return if (result.body() == null) {
                    // issue with the result body but the endpoint call has successful http code
                    -1
                } else {
                    // No error
                    null
                }
            }

            else -> {
                result.code()
            }
        }
    }

    suspend fun fetchAppointments(): List<AppointmentInfo>? {
        return tokenManager.getToken()?.let { authToken ->
            val result = fayService.fetchAppointments("Bearer $authToken")
            when {
                result.isSuccessful -> {
                    result.body()?.appointments?.map {
                        // Note: if fetchProviderName is implemented and could have errors, there should
                        // be error checking added for that too
                        AppointmentInfo(
                            it,
                            fetchProviderName(it.providerId)
                        )
                    }
                }

                else -> {
                    null
                }
            }
        }
    }

    private fun fetchProviderName(providerId: String): String {
        // A new endpoint can be used to fetch the provider name
        return "Taylor Palmer, RD"
    }
}
