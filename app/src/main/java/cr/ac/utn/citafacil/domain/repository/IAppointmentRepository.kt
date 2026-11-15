package cr.ac.utn.citafacil.domain.repository

import cr.ac.utn.citafacil.domain.model.Appointment

interface IAppointmentRepository {
    fun getAppointments(): List<Appointment>
    fun getById(id: String): Appointment?
    fun addAppointment(appointment: Appointment)
    fun updateAppointment(appointment: Appointment)
    fun deleteAppointment(id: String)
}
