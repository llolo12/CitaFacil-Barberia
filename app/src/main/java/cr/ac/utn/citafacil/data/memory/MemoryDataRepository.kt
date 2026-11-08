package cr.ac.utn.citafacil.data.memory

import cr.ac.utn.citafacil.domain.model.Appointment
import cr.ac.utn.citafacil.domain.repository.IAppointmentRepository

class MemoryDataRepository : IAppointmentRepository {
    private val appointments = mutableListOf<Appointment>()

    override fun addAppointment(appointment: Appointment) {
        appointments.add(appointment)
    }

    override fun deleteAppointment(id: String) {
        appointments.removeIf { it.id.trim() == id.trim() }
    }

    override fun updateAppointment(appointment: Appointment) {
        deleteAppointment(appointment.id)
        addAppointment(appointment)
    }

    override fun getAppointments() = appointments

    override fun getById(id: String): Appointment? {
        val result = appointments.filter { it.id.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }
}
