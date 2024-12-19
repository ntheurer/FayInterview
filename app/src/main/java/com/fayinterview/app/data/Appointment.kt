package com.fayinterview.app.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Appointment(
    @SerializedName("appointment_id")
    val appointmentId: String,
    @SerializedName("patient_id")
    val patientId: String,
    @SerializedName("provider_id")
    val providerId: String,
    val status: String,
    @SerializedName("appointment_type")
    val appointmentType: String,
    val start: Date,
    val end: Date,
    @SerializedName("duration_in_minutes")
    val durationInMinutes: Int,
    @SerializedName("recurrence_type")
    val recurrenceType: String
)
