package com.fayinterview.app.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fayinterview.app.data.FayRepository
import com.fayinterview.app.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val fayRepository: FayRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState = _uiState.asStateFlow()

    fun signIn(
        username: String,
        password: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            val errorCode = fayRepository.signIn(
                username = username,
                password = password
            )
            when (errorCode) {
                null -> {
                    // success
                    _uiState.update {
                        it.copy(
                            navForward = true
                        )
                    }
                }
                408 -> {
                    // HTTP 408 Request Timeout
                    // Tell the user to try again
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = R.string.timeout_error_message
                        )
                    }
                }
                401 -> {
                    // Bad username or password
                    // Tell the user to try again
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = R.string.unauthorized_error_message
                        )
                    }
                }
                else -> {
                    // Tell the user to try again
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = R.string.generic_error_message
                        )
                    }
                }
            }
        }
    }
}
