package com.fayinterview.app.ui.appointments

import com.fayinterview.app.data.Appointment
import java.util.Date

data class AppointmentsOverviewUiState(
    val isLoading: Boolean = true,
    val upcomingAppointments: List<AppointmentInfo> = listOf(temp),
    val pastAppointments: List<AppointmentInfo> = listOf(temp.copy(appointment = temp.appointment.copy(appointmentId = "foo", appointmentType = "Intake"))),
)

val temp = AppointmentInfo(
        appointment = Appointment(
            "509teq10vh",
            "1",
            "100",
            "Scheduled",
            "Follow-up",
            Date(),
            Date(),
            45,
            "Weekly"
        ),
        providerName = "Taylor Palmer, RD"
    )