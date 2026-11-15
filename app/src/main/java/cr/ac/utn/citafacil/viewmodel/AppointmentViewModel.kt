package cr.ac.utn.citafacil.viewmodel

import androidx.lifecycle.ViewModel
import cr.ac.utn.citafacil.domain.model.Appointment
import cr.ac.utn.citafacil.domain.repository.IAppointmentRepository

class AppointmentViewModel(
    private val repository: IAppointmentRepository
) : ViewModel() {

    fun getAppointments(): List<Appointment> {
        return repository.getAppointments()
    }

    fun getAppointmentById(id: String): Appointment? {
        return repository.getById(id)
    }

    fun saveAppointment(appointment: Appointment) {
        repository.addAppointment(appointment)
    }

    fun updateAppointment(appointment: Appointment) {
        repository.updateAppointment(appointment)
    }

    fun removeAppointment(id: String) {
        repository.deleteAppointment(id)
    }
}
