package cr.ac.utn.citafacil.domain.model

data class Appointment(
    val id: String,
    val clientName: String,
    val serviceType: ServiceType,
    val appointmentDate: String,
    val appointmentTime: String,
    val status: AppointmentStatus,
    val barberName: String,
    val notes: String
)
