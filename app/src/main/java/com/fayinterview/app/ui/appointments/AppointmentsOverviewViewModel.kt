package com.fayinterview.app.ui.appointments

import androidx.lifecycle.ViewModel
import com.fayinterview.app.data.FayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AppointmentsOverviewViewModel @Inject constructor(
    private val fayRepository: FayRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentsOverviewUiState())
    val uiState = _uiState.asStateFlow()

    // TODO: call backend to get appointments
}
