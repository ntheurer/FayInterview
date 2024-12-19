package com.fayinterview.app.ui.appointments

data class AppointmentsOverviewUiState(
    val isLoading: Boolean = true,
    val upcomingAppointments: List<AppointmentInfo> = emptyList(),
    val pastAppointments: List<AppointmentInfo> = emptyList(),
    val logOut: Boolean = false
)
