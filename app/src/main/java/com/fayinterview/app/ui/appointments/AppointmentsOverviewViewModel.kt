package com.fayinterview.app.ui.appointments

import android.util.Log
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
class AppointmentsOverviewViewModel @Inject constructor(
    private val fayRepository: FayRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(AppointmentsOverviewUiState())
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            fayRepository.fetchAppointments()?.let { appointments ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        upcomingAppointments = appointments.filter {
                            !it.isPast
                        },
                        pastAppointments = appointments.filter {
                            it.isPast
                        }
                    )
                }
            } ?: run {
                Log.w(
                    "AppointmentsOverview",
                    "Logging user out since they are not authenticated"
                )
                _uiState.update {
                    it.copy(
                        logOut = true
                    )
                }
            }
        }
    }
}
