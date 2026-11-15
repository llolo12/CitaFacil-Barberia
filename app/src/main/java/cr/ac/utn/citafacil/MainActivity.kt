package cr.ac.utn.citafacil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import util.util

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnAgenda = findViewById<Button>(R.id.btnAgenda_main)
        val btnRegister = findViewById<Button>(R.id.btnRegister_main)
        val btnLocation = findViewById<Button>(R.id.btnLocation_main)

        btnAgenda.setOnClickListener {
            util.OpenActivity(this, AgendaActivity::class.java)
        }

        btnRegister.setOnClickListener {
            util.OpenActivity(this, RegisterAppointmentActivity::class.java)
        }

        btnLocation.setOnClickListener {
            util.OpenActivity(this, LocationActivity::class.java)
        }
    }
}
