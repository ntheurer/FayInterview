package com.fayinterview.app.ui.appointments

import com.fayinterview.app.data.Appointment
import com.fayinterview.app.util.isPast

data class AppointmentInfo(
    val appointment: Appointment,
    val providerName: String
) {
    val isPast = appointment.end.isPast()
}
