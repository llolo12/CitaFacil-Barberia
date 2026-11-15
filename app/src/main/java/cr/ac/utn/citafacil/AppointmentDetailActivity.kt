package cr.ac.utn.citafacil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AppointmentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnEdit = findViewById<Button>(R.id.btnEdit_Detail)
        val btnDelete = findViewById<Button>(R.id.btnDelete_Detail)

        btnEdit.setOnClickListener {
            val intent = Intent(this, RegisterAppointmentActivity::class.java)
            startActivity(intent)
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_message)
                .setPositiveButton(R.string.btn_delete_appointment) { _, _ ->
                    Toast.makeText(this, R.string.msg_appointment_deleted, Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton(R.string.btn_cancel, null)
                .show()
        }
    }
}
