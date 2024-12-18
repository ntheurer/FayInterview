package com.fayinterview.app.ui.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fayinterview.app.data.FayRepository
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
                    isLoading = true
                )
            }
            val isSuccess = fayRepository.signIn(
                username = username,
                password = password
            )
            if (isSuccess) {
                _uiState.update {
                    it.copy(
                        navForward = true
                    )
                }
            } else {
                // TODO: Nice to have - error message for the user since it failed
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
}
