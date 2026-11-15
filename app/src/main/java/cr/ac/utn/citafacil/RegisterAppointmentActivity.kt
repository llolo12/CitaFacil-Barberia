package cr.ac.utn.citafacil

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cr.ac.utn.citafacil.data.memory.MemoryDataRepository
import cr.ac.utn.citafacil.domain.model.Appointment
import cr.ac.utn.citafacil.domain.model.AppointmentStatus
import cr.ac.utn.citafacil.domain.model.ServiceType
import cr.ac.utn.citafacil.viewmodel.AppointmentViewModel
import java.util.UUID

class RegisterAppointmentActivity : AppCompatActivity() {

    private lateinit var viewModel: AppointmentViewModel
    private lateinit var etId: EditText
    private lateinit var etClientName: EditText
    private lateinit var etPhone: EditText
    private lateinit var spinnerService: Spinner
    private lateinit var etDate: EditText
    private lateinit var etTime: EditText

    private lateinit var btnSearch: Button
    private lateinit var btnSave: Button
    private lateinit var btnRemove: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val repository = MemoryDataRepository()
        viewModel = AppointmentViewModel(repository)

        etId = findViewById(R.id.etId_Register)
        etClientName = findViewById(R.id.etClientName_Register)
        etPhone = findViewById(R.id.etPhone_Register)
        spinnerService = findViewById(R.id.spinnerService_Register)
        etDate = findViewById(R.id.etDate_Register)
        etTime = findViewById(R.id.etTime_Register)

        btnSearch = findViewById(R.id.btnSearch_Register)
        btnSave = findViewById(R.id.btnSave_Register)
        btnRemove = findViewById(R.id.btnRemove_Register)

        // Setup Spinner Adapter for services
        val services = listOf("Classic Haircut", "Beard Trim", "Haircut & Beard", "Hair Coloring")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, services)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerService.adapter = adapter

        // Search action for CRUD Edit mode
        btnSearch.setOnClickListener {
            val id = etId.text.toString().trim()
            if (id.isNotEmpty()) {
                val appointment = viewModel.getAppointmentById(id)
                if (appointment != null) {
                    etClientName.setText(appointment.clientName)
                    etPhone.setText(appointment.barberName)
                    etDate.setText(appointment.appointmentDate)
                    etTime.setText(appointment.appointmentTime)
                    Toast.makeText(this, "Appointment found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.msg_appointment_not_found, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter an ID to search", Toast.LENGTH_SHORT).show()
            }
        }

        // Save / Update action
        btnSave.setOnClickListener {
            val name = etClientName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val dateStr = etDate.text.toString().trim()
            val timeStr = etTime.text.toString().trim()
            val idInput = etId.text.toString().trim()
            val selectedServiceIndex = spinnerService.selectedItemPosition

            val serviceType = when (selectedServiceIndex) {
                0 -> ServiceType.HAIRCUT
                1 -> ServiceType.BEARD_TRIM
                2 -> ServiceType.HAIRCUT_AND_BEARD
                else -> ServiceType.HAIR_COLORING
            }

            if (name.isNotEmpty() && timeStr.isNotEmpty()) {
                val appointmentId = if (idInput.isNotEmpty()) idInput else UUID.randomUUID().toString()
                val dateFinal = dateStr.ifEmpty { "18 Jun" }

                val appointment = Appointment(
                    id = appointmentId,
                    clientName = name,
                    serviceType = serviceType,
                    appointmentDate = dateFinal,
                    appointmentTime = timeStr,
                    status = AppointmentStatus.CONFIRMED,
                    barberName = phone.ifEmpty { "Barber 1" },
                    notes = "Reference photo attached"
                )

                if (idInput.isNotEmpty() && viewModel.getAppointmentById(idInput) != null) {
                    viewModel.updateAppointment(appointment)
                    Toast.makeText(this, R.string.msg_appointment_updated, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.saveAppointment(appointment)
                    Toast.makeText(this, R.string.msg_appointment_saved, Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(this, R.string.msg_fill_fields, Toast.LENGTH_SHORT).show()
            }
        }

        // Delete action with confirmation dialog (Task 5 requirement)
        btnRemove.setOnClickListener {
            val id = etId.text.toString().trim()
            if (id.isNotEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle(R.string.dialog_delete_title)
                    .setMessage(R.string.dialog_delete_message)
                    .setPositiveButton(R.string.btn_delete_appointment) { _, _ ->
                        viewModel.removeAppointment(id)
                        Toast.makeText(this, R.string.msg_appointment_deleted, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .setNegativeButton(R.string.btn_cancel, null)
                    .show()
            } else {
                Toast.makeText(this, "Enter appointment ID to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
