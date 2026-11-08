package cr.ac.utn.citafacil.domain.model

import java.time.LocalDate

data class Appointment(
    val id: String,
    val clientName: String,
    val serviceType: ServiceType,
    val appointmentDate: LocalDate,
    val appointmentTime: String,
    val status: AppointmentStatus,
    val barberName: String,
    val notes: String
)
