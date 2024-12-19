package com.fayinterview.app.ui.welcome

import androidx.annotation.StringRes

data class WelcomeUiState(
    val isLoading: Boolean = false,
    val navForward: Boolean = false,
    @StringRes val errorMessage: Int? = null
)
